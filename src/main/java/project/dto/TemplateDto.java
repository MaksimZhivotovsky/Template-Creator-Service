package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDto {

    @NotBlank
    private String name;
//    @NotNull(message = "Нужно указать ID организации")
    private Long organizationId;
    private List<ValueDto> values = new ArrayList<>();

}
