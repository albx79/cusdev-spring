package it.albx79.telepass.cusdev.devices;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface DevicesRepo extends CrudRepository<DeviceDto, UUID> {
    List<DeviceDto> findAllByCustomerId(String customerId);
}
