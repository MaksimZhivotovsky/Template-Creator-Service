package project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.Template;
import project.utils.ObjectMapperUtil;
import project.utils.ParseJson;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO запроса для до создание шаблона")
public class PostCreateTemplateDto {

    @Schema(description = "JSON строка для до создания шаблона")
    @NotBlank
    private String jsonValue;
    @Schema(description = "Шаблон к которому относится эта строка для до создания шаблона")
    @NotBlank
    private Template template;

    public void setJsonValue(Object jsonValue) {
        this.jsonValue = ObjectMapperUtil.setValue(jsonValue);
    }

    public Object getJsonValue() {
        return ParseJson.parse(this.jsonValue);
    }
}
