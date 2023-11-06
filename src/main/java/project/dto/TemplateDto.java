package project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import project.utils.ObjectMapperUtil;
import project.utils.ParseJson;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "DTO Шаблона")
public class TemplateDto {

    @Schema(description = "имя шаблона")
    @NotBlank
    private String name;
    @Schema(description = "JSON создания шаблона")
    @NotBlank
    private String jsonTemplate;
    @Schema(description = "JSON запроса для до создания шаблона")
    @NotBlank
    private String postCreateTemplate;

    public void setJsonTemplate(Object jsonTemplate) {
        this.jsonTemplate = ObjectMapperUtil.setValue(jsonTemplate);
    }

    public void setPostCreateTemplate(Object jsonTemplate) {
        this.postCreateTemplate = ObjectMapperUtil.setValue(jsonTemplate);
    }

    public Object getJsonTemplate() {
        return ParseJson.parse(this.jsonTemplate);
    }

    public Object getPostCreateTemplate() {
        return ParseJson.parse(this.postCreateTemplate);
    }

}
