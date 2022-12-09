package it.albx79.telepass.cusdev.devices;

import it.albx79.telepass.cusdev.api.DevicesApiDelegate;
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
}
