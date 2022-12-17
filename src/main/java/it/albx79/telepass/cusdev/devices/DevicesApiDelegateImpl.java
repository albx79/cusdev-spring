package it.albx79.telepass.cusdev.devices;

import it.albx79.telepass.cusdev.api.DevicesApiDelegate;
import it.albx79.telepass.cusdev.error.PreconditionFailedException;
import it.albx79.telepass.cusdev.error.ResourceNotFoundException;
import it.albx79.telepass.cusdev.model.Device;
import it.albx79.telepass.cusdev.model.UpdateStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DevicesApiDelegateImpl implements DevicesApiDelegate {
    private final DevicesRepo devicesRepo;

    @Override
    public ResponseEntity<Boolean> deviceExists(UUID deviceCode) {
        var exists = devicesRepo.findById(deviceCode).isPresent();
        return ResponseEntity.ok(exists);
    }

    @Override
    public ResponseEntity<Void> updateDeviceStatus(UUID deviceCode, UpdateStatusRequest updateStatusRequest) {
        var device = devicesRepo.findById(deviceCode)
                .orElseThrow(() -> new ResourceNotFoundException("Device " + deviceCode));
        device.setStatus(updateStatusRequest.getStatus());
        devicesRepo.save(device);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Device> createDevice(Device device) {
        var currentDevices = devicesRepo.findAllByCustomerId(device.getCustomer());
        if (currentDevices.size() >= 2) {
            throw new PreconditionFailedException("Maximum number of devices reached");
        }
        var created = devicesRepo.save(new DeviceDto(null, device.getStatus(), device.getCustomer()));
        var result = new Device()
                .code(created.getCode())
                .status(created.getStatus());
        return ResponseEntity.ok(result);
    }
}
