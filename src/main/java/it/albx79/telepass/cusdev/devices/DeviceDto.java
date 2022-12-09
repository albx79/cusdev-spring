package it.albx79.telepass.cusdev.devices;

import it.albx79.telepass.cusdev.model.DeviceStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
public class DeviceDto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID code;
    private DeviceStatus status;
    private String customerId;
}
