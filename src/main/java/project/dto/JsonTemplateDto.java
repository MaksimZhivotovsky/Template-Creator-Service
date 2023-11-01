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
public class JsonTemplateDto {

    private String jsonValue;
    private Template template;

    public void setJsonValue(Object jsonValue) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.jsonValue = objectMapper.writeValueAsString(jsonValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
