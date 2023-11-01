package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.Template;
import project.utils.ObjectMapperUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTemplateDto {

    private String jsonValue;
    private Template template;

    public void setJsonValue(Object jsonValue) {
        this.jsonValue = ObjectMapperUtil.setValue(jsonValue);
    }
}
