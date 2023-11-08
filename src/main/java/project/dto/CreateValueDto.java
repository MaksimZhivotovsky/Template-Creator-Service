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
@Schema(description = "DTO запроса для создания шаблона")

@JsonIgnoreProperties(value = {"value"})
public class CreateValueDto {

    @Schema(description = "JSON строка шаблона")
    @NotBlank
    private String jsonValue;
    @Schema(description = "Value к которому относится эта строка")
//    @NotBlank
    private Value value;

    public void setJsonValue(Object jsonValue) {
        this.jsonValue = ObjectMapperUtil.setValue(jsonValue);
    }
    public Object getJsonValue() {
        return ParseJson.parse(this.jsonValue);
    }
}
