package project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.management.MXBean;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class UserRcDto {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String keycloakId;
    private Long organizationId;
}
