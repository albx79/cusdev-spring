package it.albx79.telepass.cusdev.devices;

import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.annotations.Fixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DevicesApiDelegateImplTest {
    DevicesApiDelegateImpl devices;

    @Mock
    DevicesRepo devicesRepo;

    @Fixture
    DeviceDto aDevice;

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
}