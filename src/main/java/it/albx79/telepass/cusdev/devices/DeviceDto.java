package it.albx79.telepass.cusdev.devices;

import it.albx79.telepass.cusdev.model.DeviceStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Getter
@Setter
public class DeviceDto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID code;
    private DeviceStatus status;
    private String customerId;

    protected DeviceDto(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DeviceDto deviceDto = (DeviceDto) o;
        return code != null && Objects.equals(code, deviceDto.code);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
