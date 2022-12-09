package it.albx79.telepass.cusdev.customers;

import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.annotations.Fixture;
import it.albx79.telepass.cusdev.devices.DeviceDto;
import it.albx79.telepass.cusdev.devices.DevicesRepo;
import it.albx79.telepass.cusdev.model.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomersApiDelegateImplTest {

    CustomersApiDelegateImpl customers;

    @Mock
    CustomersRepo customersRepo;
    @Mock
    DevicesRepo devicesRepo;

    @Fixture
    List<DeviceDto> someDevices;
    @Fixture
    CustomerDto aCustomer;

    @BeforeEach
    void setup() {
        FixtureAnnotations.initFixtures(this);
        customers = new CustomersApiDelegateImpl(customersRepo, devicesRepo);

        when(customersRepo.findById(aCustomer.getId())).thenReturn(Optional.of(aCustomer));
    }

    @Test
    void retrieveCustomer_without_aggregateDevices_returns_only_customer_data() {
        var customer = customers.getCustomer(aCustomer.getId(), Optional.empty()).getBody();
        assertNull(customer.getDevices());
    }

    @Test
    void retrieveCustomer_with_aggregateDevices_false_returns_only_customer_data() {
        var customer = customers.getCustomer(aCustomer.getId(), Optional.of(false)).getBody();
        assertNull(customer.getDevices());
    }

    @Test
    void retrieveCustomer_with_aggregateDevices_true_returns_customer_and_devices_data() {
        when(devicesRepo.findAllByCustomerId(aCustomer.getId())).thenReturn(someDevices);

        var customer = customers.getCustomer(aCustomer.getId(), Optional.of(true)).getBody();
        assertEquals(
                someDevices.stream().map(DeviceDto::getCode).toList(),
                customer.getDevices().stream().map(Device::getCode).toList()
        );
    }
}