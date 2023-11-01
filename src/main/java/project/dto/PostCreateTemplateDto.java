package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.Template;
import project.utils.ObjectMapperUtil;
import project.utils.ParseJson;

import javax.persistence.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateTemplateDto {

    private String jsonValue;
    private Template template;

    public void setJsonValue(Object jsonValue) {
        this.jsonValue = ObjectMapperUtil.setValue(jsonValue);
    }
    public Object getJsonValue() {
        return ParseJson.parse(this.jsonValue);
    }
}
