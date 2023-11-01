package project.mapper;

import project.dto.JsonTemplateDto;
import project.entity.JsonTemplate;

import java.util.Date;

public class JsonTemplateMapper {

    // Convert JsonTemplate JPA Entity into JsonTemplateDto
    public static JsonTemplateDto mapToJsonTemplateDto(JsonTemplate jsonTemplate) {
        return new JsonTemplateDto(
                jsonTemplate.getJsonValue(),
                jsonTemplate.getTemplate()
        );
    }

    // Convert JsonTemplateDto into User JPA JsonTemplate
    public static JsonTemplate mapToJsonTemplate(JsonTemplateDto jsonTemplateDto) {
        JsonTemplate jsonTemplate = new JsonTemplate();
//        jsonTemplate.setJsonValue(jsonTemplateDto.getJsonValue());
        jsonTemplate.setJsonValue(jsonTemplateDto.getJsonValue());
        jsonTemplate.setTemplate(jsonTemplateDto.getTemplate());
        jsonTemplate.setTimestamp(new Date());
        jsonTemplate.setIsArchive(false);
        return jsonTemplate;
    }
}
