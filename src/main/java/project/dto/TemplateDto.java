package project.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import project.entity.JsonTemplate;
import project.entity.PostCreateTemplate;
import project.mapper.JsonTemplateMapper;
import project.mapper.PostCreateTemplateMapper;

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
        JsonTemplate jsonTemplate = JsonTemplateMapper.mapToJsonTemplate(jsonTemplateDto);
        jsonTemplates.add(jsonTemplate);
        return jsonTemplate;
    }

    public PostCreateTemplate setPostCreateTemplates(PostCreateTemplateDto postCreateTemplateDto) {
        PostCreateTemplate postCreateTemplate = PostCreateTemplateMapper.mapToPostCreateTemplate(postCreateTemplateDto);
        postCreateTemplates.add(postCreateTemplate);
        return postCreateTemplate;
    }
}
