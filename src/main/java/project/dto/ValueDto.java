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
    private String createValue;
    @Schema(description = "JSON запроса для до создания шаблона")
    @NotBlank
    private String updateValue;
    @NotNull(message = "Нужно указать ID сервиса")
    private Long serviceId;


    public void setCreateValue(Object createValue) {
        this.createValue = ObjectMapperUtil.setValue(createValue);
    }

    public void setUpdateValue(Object updateValue) {
        this.updateValue = ObjectMapperUtil.setValue(updateValue);
    }

    public Object getCreateValue() {
        return ParseJson.parse(this.createValue);
    }

    public Object getUpdateValue() {
        return ParseJson.parse(this.updateValue);
    }

}
