package project.mapper;

import project.dto.TemplateDto;
import project.entity.Template;

public class TemplateMapper {

    private TemplateMapper() {}
    // Convert Template JPA Entity into TemplateDto
    public static TemplateDto mapToTemplateDto(Template template) {
        return new TemplateDto(
                template.getName(),
                template.getJsonTemplate(),
                template.getPostCreateTemplate()
        );

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
