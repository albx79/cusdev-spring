package it.albx79.telepass.cusdev.devices;

import it.albx79.telepass.cusdev.api.DevicesApiDelegate;
import it.albx79.telepass.cusdev.model.Device;
import it.albx79.telepass.cusdev.model.UpdateStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

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
        var device = devicesRepo.findById(deviceCode).get();
        device.setStatus(updateStatusRequest.getStatus());
        devicesRepo.save(device);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Device> createDevice(Device device) {
        var created = devicesRepo.save(new DeviceDto(device.getCode(), device.getStatus(), device.getCustomer()));
        var result = new Device()
                .code(created.getCode())
                .status(created.getStatus());
        return ResponseEntity.ok(result);
    }
}
