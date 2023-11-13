package project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import project.utils.ObjectMapperUtil;
import project.utils.ParseJson;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "DTO Value")
public class ValueDto {

    @Schema(description = "JSON создания шаблона")
    @NotBlank
    private String jsonValue;
    @Schema(description = "JSON запроса для до создания шаблона")
    @NotBlank
    private String updateValue;
    @NotNull(message = "Нужно указать ID сервиса")
    private Long serviceId;
//    @NotNull(message = "Нужно указать ID организации")
    private Long organizationId;


    public void setJsonValue(Object jsonValue) {
        this.jsonValue = ObjectMapperUtil.setValue(jsonValue);
    }

    public void setUpdateValue(Object updateValue) {
        this.updateValue = ObjectMapperUtil.setValue(updateValue);
    }

}
