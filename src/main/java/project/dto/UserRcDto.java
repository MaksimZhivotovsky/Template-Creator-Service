package project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

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
