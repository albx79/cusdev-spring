package it.albx79.telepass.cusdev.devices;

import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.annotations.Fixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DevicesRepoTest {
    @Autowired
    DevicesRepo devicesRepo;

    @Fixture
    DeviceDto aDevice;

    @BeforeEach
    void setup() {
        FixtureAnnotations.initFixtures(this);
    }

    @Test
    void devicesRepo_stores_and_retrieves_data_by_id() {
        aDevice = devicesRepo.save(aDevice);
        var loadedDevice = devicesRepo.findById(aDevice.getCode()).get();

        assertEquals(aDevice.getStatus(), loadedDevice.getStatus());
    }

    @Test
    void devicesRepo_stores_and_retrieves_data_by_customerId() {
        aDevice = devicesRepo.save(aDevice);
        var loadedDevices = devicesRepo.findAllByCustomerId(aDevice.getCustomerId());

        assertEquals(singletonList(aDevice), loadedDevices);
    }
}