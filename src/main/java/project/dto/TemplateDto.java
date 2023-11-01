package project.dto;

import lombok.*;
import project.utils.ObjectMapperUtil;
import project.utils.ParseJson;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDto {

    private String name;
    private String jsonTemplate;
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
