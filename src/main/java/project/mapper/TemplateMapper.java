package project.mapper;

import project.dto.TemplateDto;
import project.entity.Template;

public class TemplateMapper {

    private TemplateMapper() {
    }

    // Convert Template JPA Entity into TemplateDto
    public static TemplateDto mapToValueDto(Template template) {
        TemplateDto templateDto = new TemplateDto();
        templateDto.setName(template.getName());
        templateDto.setServiceId(template.getServiceId());
        templateDto.setServiceId(templateDto.getServiceId());
        templateDto.setJsonValue(template.getJsonValue());
        templateDto.setUpdateJsonValue(template.getUpdateJsonValue());

        return templateDto;
    }

    // Convert TemplateDto into User JPA Template
    public static Template mapToValue(TemplateDto templateDto) {
        Template template = new Template();
        template.setName(templateDto.getName());
        template.setServiceId(templateDto.getServiceId());
        template.setUpdateJsonValue(templateDto.getUpdateJsonValue());
        template.setJsonValue(templateDto.getJsonValue());
        template.setIsArchive(false);
        template.setOrganizationId(templateDto.getOrganizationId());
        return template;
    }
}
