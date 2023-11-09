package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDto {

    @NotBlank
    private String templateName;
    @NotNull(message = "Нужно указать ID сервиса")
    private Long serviceId;
    private List<ValueDto> valueDtoList = new ArrayList<>();

}
