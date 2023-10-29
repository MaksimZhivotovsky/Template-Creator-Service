package project.mapper;

import project.dto.JsonTemplateDto;
import project.dto.PostCreateTemplateDto;
import project.entity.JsonTemplate;
import project.entity.PostCreateTemplate;

import java.util.Date;

public class PostCreateTemplateMapper {


    // Convert PostCreateTemplate JPA Entity into PostCreateTemplateDto
    public static PostCreateTemplateDto mapToPostCreateTemplateDto(PostCreateTemplate postCreateTemplate) {
        return new PostCreateTemplateDto(
                postCreateTemplate.getJsonValue(),
                postCreateTemplate.getTemplate()
        );
    }

    // Convert PostCreateTemplateDto into User JPA PostCreateTemplate
    public static PostCreateTemplate mapToPostCreateTemplate(PostCreateTemplateDto postCreateTemplateDto) {
        PostCreateTemplate postCreateTemplate = new PostCreateTemplate();
        postCreateTemplate.setJsonValue(postCreateTemplateDto.getJsonValue());
        postCreateTemplate.setTemplate(postCreateTemplateDto.getTemplate());
        postCreateTemplate.setTimestamp(new Date());
        postCreateTemplate.setIsArchive(false);
        return postCreateTemplate;
    }
}
