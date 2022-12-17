package it.albx79.telepass.cusdev.customers;

import it.albx79.telepass.cusdev.api.CustomersApiDelegate;
import it.albx79.telepass.cusdev.devices.DevicesRepo;
import it.albx79.telepass.cusdev.error.ResourceNotFoundException;
import it.albx79.telepass.cusdev.model.Customer;
import it.albx79.telepass.cusdev.model.Device;
import it.albx79.telepass.cusdev.model.UpdateAddressRequest;
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
    public ResponseEntity<Customer> getCustomer(Integer customerId, Optional<Boolean> aggregateDevices) {
        var dto = customers.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + customerId));
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

    @Override
    public ResponseEntity<Customer> createCustomer(Customer customer) {
        var created = customers.save(new CustomerDto(
                -1, customer.getName(), customer.getSurname(), customer.getAddress(), customer.getTaxCode()
        ));
        return ResponseEntity.ok(new Customer()
                .name(created.getName())
                .surname(created.getSurname())
                .address(created.getAddress())
                .taxCode(created.getTaxCode())
                .id(created.getId())
        );
    }

    @Override
    public ResponseEntity<Void> updateCustomerAddress(Integer customerId, UpdateAddressRequest request) {
        var customer = customers.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + customerId));
        customer.setAddress(request.getAddress());
        customers.save(customer);
        return ResponseEntity.ok(null);
    }
}
