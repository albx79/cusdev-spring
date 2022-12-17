package it.albx79.telepass.cusdev.devices;

import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.annotations.Fixture;
import it.albx79.telepass.cusdev.error.PreconditionFailedException;
import it.albx79.telepass.cusdev.model.Device;
import it.albx79.telepass.cusdev.model.UpdateStatusRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DevicesApiDelegateImplTest {
    DevicesApiDelegateImpl devices;

    @Mock
    DevicesRepo devicesRepo;

    @Fixture
    DeviceDto aDevice;
    @Fixture
    UpdateStatusRequest newStatus;

    @BeforeEach
    void setup() {
        devices = new DevicesApiDelegateImpl(devicesRepo);

        FixtureAnnotations.initFixtures(this);
    }

    @Test
    void deviceExists_verify_existence() {
        when(devicesRepo.findById(aDevice.getCode())).thenReturn(Optional.of(aDevice));

        var result = devices.deviceExists(aDevice.getCode()).getBody();

        assertTrue(result);
    }

    @Test
    void deviceExists_verify_non_existence() {
        when(devicesRepo.findById(aDevice.getCode())).thenReturn(Optional.empty());
        var result = devices.deviceExists(aDevice.getCode()).getBody();
        assertFalse(result);
    }

    @Test
    void updateDeviceStatus_updates_the_status() {
        when(devicesRepo.findById(aDevice.getCode())).thenReturn(Optional.of(aDevice));

        devices.updateDeviceStatus(aDevice.getCode(), newStatus);

        verify(devicesRepo).save(argThat(d -> d.getStatus() == newStatus.getStatus()));
    }

    @Test
    void createDevice_when_customer_has_two_devices_throws_PreconditionFailedException() {
        var fixture = new JFixture();
        when(devicesRepo.findAllByCustomerId(aDevice.getCustomerId())).thenReturn(List.of(
                fixture.create(DeviceDto.class),
                fixture.create(DeviceDto.class)
        ));
        var device = new Device()
                .customer(aDevice.getCustomerId())
                .code(aDevice.getCode())
                .status(aDevice.getStatus());

        assertThrows(PreconditionFailedException.class, () -> devices.createDevice(device));
    }
}