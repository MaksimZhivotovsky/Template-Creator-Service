package project.dto;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@ToString(of = {"firstName", "lastName", "middleName"})
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
