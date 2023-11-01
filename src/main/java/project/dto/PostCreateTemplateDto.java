package project.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.Template;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateTemplateDto {

    private String jsonValue;
    private Template template;

    public void setJsonValue(Object jsonValue) {
        ObjectMapper Obj = new ObjectMapper();
        try {
            this.jsonValue = Obj.writeValueAsString(jsonValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
