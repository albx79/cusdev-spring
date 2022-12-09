package it.albx79.telepass.cusdev.customers;

import it.albx79.telepass.cusdev.api.CustomersApiDelegate;
import it.albx79.telepass.cusdev.devices.DevicesRepo;
import it.albx79.telepass.cusdev.model.Customer;
import it.albx79.telepass.cusdev.model.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomersApiDelegateImpl implements CustomersApiDelegate {

    private final CustomersRepo customers;
    private final DevicesRepo devices;

    @Override
    public ResponseEntity<Customer> getCustomer(String customerId, Optional<Boolean> aggregateDevices) {
        var dto = customers.findById(customerId).get();
        var aggregatedDevices = aggregateDevices.orElse(false) ?
                devices.findAllByCustomerId(customerId).stream().map(d -> new Device()
                        .code(d.getCode())
                        .status(d.getStatus())
                ).toList() :
                null;
        var customer = new Customer()
                .address(dto.getAddress())
                .name(dto.getName())
                .surname(dto.getSurname())
                .taxCode(dto.getTaxCode())
                .id(dto.getId())
                .devices(aggregatedDevices);
        return ResponseEntity.ok(customer);

    }
}
