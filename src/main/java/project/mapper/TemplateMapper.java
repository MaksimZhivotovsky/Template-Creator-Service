package project.mapper;

import project.dto.TemplateDto;
import project.entity.Template;

public class TemplateMapper {

    private TemplateMapper() {}

    // Convert Template JPA Entity into TemplateDto
    public static TemplateDto mapToTemplateDto(Template template) {
        TemplateDto templateDto = new TemplateDto();
        templateDto.setName(template.getName());
        templateDto.setJsonTemplate(template.getJsonTemplate());
        templateDto.setPostCreateTemplate(template.getPostCreateTemplate());

        return templateDto;
    }

    // Convert TemplateDto into User JPA Template
    public static Template mapToTemplate(TemplateDto templateDto) {
        Template template = new Template();

        template.setName(templateDto.getName());
        template.setJsonTemplate(templateDto.getJsonTemplate());
        template.setPostCreateTemplate(templateDto.getPostCreateTemplate());
        template.setIsArchive(false);
        return template;
    }
}
