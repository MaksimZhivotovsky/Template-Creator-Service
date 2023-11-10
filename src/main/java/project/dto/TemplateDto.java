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

    @Schema(description = "JSON создания шаблона")
    @NotBlank
    private String createValue;
    @Schema(description = "JSON запроса для до создания шаблона")
    @NotBlank
    private String updateValue;
    @NotNull(message = "Нужно указать ID сервиса")
    private Long serviceId;
    @NotNull(message = "Нужно указать ID организации")
    private Long organizationId;


    public void setCreateValue(Object createValue) {
        this.createValue = ObjectMapperUtil.setValue(createValue);
    }

    public void setUpdateValue(Object updateValue) {
        this.updateValue = ObjectMapperUtil.setValue(updateValue);
    }

}
