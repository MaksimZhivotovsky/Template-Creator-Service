package project.mapper;

import project.dto.TemplateDto;
import project.entity.Template;

import java.time.LocalDateTime;

public class TemplateMapper {

    private TemplateMapper() {
    }

    // Convert Template JPA Entity into TemplateDto
    public static TemplateDto mapToValueDto(Template value) {
        TemplateDto templateDto = new TemplateDto();
        templateDto.setServiceId(value.getServiceId());
        templateDto.setServiceId(templateDto.getServiceId());
        templateDto.setCreateValue(value.getCreateValue());
        templateDto.setUpdateValue(value.getUpdateValue());

        return templateDto;
    }

    // Convert TemplateDto into User JPA Template
    public static Template mapToValue(TemplateDto templateDto) {
        Template template = new Template();
        template.setServiceId(templateDto.getServiceId());
        template.setUpdateValue(templateDto.getUpdateValue());
        template.setCreateValue(templateDto.getCreateValue());
        template.setIsArchive(false);
        template.setOrganizationId(templateDto.getOrganizationId());
//        template.setCreateData(LocalDateTime.now());
//        template.setModifyData(LocalDateTime.now());
        return template;
    }
}
