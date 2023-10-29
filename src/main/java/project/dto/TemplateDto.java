package project.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import project.entity.JsonTemplate;
import project.entity.PostCreateTemplate;
import project.mapper.JsonTemplateMapper;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDto {

    private String name;
    private String jsonTemplate;
    private String postCreateTemplate;

    private List<JsonTemplate> jsonTemplates;

    private List<PostCreateTemplate> postCreateTemplates;

    public void setJsonTemplate(Object jsonTemplate) {
        ObjectMapper Obj = new ObjectMapper();
        try {
            this.jsonTemplate = Obj.writeValueAsString(jsonTemplate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPostCreateTemplate(Object jsonTemplate) {
        ObjectMapper Obj = new ObjectMapper();
        try {
            this.postCreateTemplate = Obj.writeValueAsString(jsonTemplate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonTemplate setJsonTemplates(JsonTemplateDto jsonTemplateDto) {
        JsonTemplate jsonTemplate1 = JsonTemplateMapper.mapToJsonTemplate(jsonTemplateDto);
        jsonTemplates.add(jsonTemplate1);
        return jsonTemplate1;
    }

    public PostCreateTemplate setPostCreateTemplates(PostCreateTemplate postCreateTemplate) {
        postCreateTemplates.add(postCreateTemplate);
        return postCreateTemplate;
    }
}
