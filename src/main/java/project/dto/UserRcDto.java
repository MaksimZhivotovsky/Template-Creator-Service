package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRcDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String keycloakId;
    private Long organizationId;
}
