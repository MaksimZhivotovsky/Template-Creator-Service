package project.mapper;

import project.dto.TemplateDto;
import project.dto.ValueDto;
import project.entity.Template;
import project.entity.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TemplateMapper {

    private TemplateMapper() {}

    // Convert Template JPA Entity into TemplateDto

    public static TemplateDto mapToTemplateDto(Template template) {
        TemplateDto templateDto = new TemplateDto();
        templateDto.setName(template.getName());
        templateDto.setOrganizationId(template.getOrganizationId());

        List<ValueDto> valueDtoList = new ArrayList<>();
        for(Value value : template.getValues()) {
            valueDtoList.add(ValueMapper.mapToValueDto(value));
        }

        templateDto.setValueDtoList(valueDtoList);
        return templateDto;
    }

    // Convert TemplateDto JPA Entity into Template

    public static Template mapToTemplate(TemplateDto templateDto) {
        Template template = new Template();
        template.setName(templateDto.getName());

        List<Value> values = new ArrayList<>();
        for (ValueDto valueDto : templateDto.getValueDtoList()) {
            values.add(ValueMapper.mapToValue(valueDto));
        }

        template.setValues(values);
        template.setCreateData(LocalDateTime.now());
        template.setOrganizationId(templateDto.getOrganizationId());
        template.setIsArchive(false);

        return template;
    }

}
