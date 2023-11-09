package project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.Value;
import project.utils.ObjectMapperUtil;
import project.utils.ParseJson;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO запроса для до создание шаблона")
@JsonIgnoreProperties(value = {"value"})
public class UpdateValueDto {

    @Schema(description = "JSON строка для до создания шаблона")
    @NotBlank
    private String jsonValue;
    @Schema(description = "Шаблон к которому относится эта строка для до создания шаблона")
    private Value value;

    public void setJsonValue(Object jsonValue) {
        this.jsonValue = ObjectMapperUtil.setValue(jsonValue);
    }

    public Object getJsonValue() {
        return ParseJson.parse(this.jsonValue);
    }
}
