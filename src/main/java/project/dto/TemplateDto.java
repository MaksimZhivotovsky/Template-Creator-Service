package project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import project.utils.ObjectMapperUtil;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "DTO Template")
@ToString(of = {"createValue", "updateValue"})
public class TemplateDto {

    @Schema(description = "Имя шаблона")
    @NotBlank
    private String name;
    @Schema(description = "JSON создания шаблона")
    @NotBlank
    private String jsonValue;
    @Schema(description = "JSON запроса для до создания шаблона")
    @NotBlank
    private String updateJsonValue;
    @NotNull(message = "Нужно указать ID сервиса")
    private Long serviceId;
    @NotNull(message = "Нужно указать ID организации")
    private Long organizationId;


    public void setJsonValue(Object jsonValue) {
        this.jsonValue = ObjectMapperUtil.setValue(jsonValue);
    }

    public void setUpdateJsonValue(Object updateJsonValue) {
        this.updateJsonValue = ObjectMapperUtil.setValue(updateJsonValue);
    }

}
