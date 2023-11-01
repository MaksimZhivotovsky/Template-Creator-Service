package project.dto;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.IOException;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateDto {

    private String name;
    private String jsonTemplate;
    private String postCreateTemplate;

    public void setJsonTemplate(Object jsonTemplate) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.jsonTemplate = objectMapper.writeValueAsString(jsonTemplate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public Object getJsonTemplateObject() {
        return parserJson(this.jsonTemplate);
    }

    public void setPostCreateTemplate(Object jsonTemplate) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.postCreateTemplate = objectMapper.writeValueAsString(jsonTemplate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getPostCreateTemplateObject() {
        return parserJson(this.postCreateTemplate);
    }



    private Object parserJson(String parse) {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser;
        try {
            parser = factory.createParser(parse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonNode actualObj;
        try {
            actualObj = mapper.readTree(parser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return actualObj;
    }
}
