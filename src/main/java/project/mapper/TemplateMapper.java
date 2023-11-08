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
        templateDto.setTemplateName(template.getTemplateName());
        templateDto.setServiceId(template.getServiceId());

        List<ValueDto> valueDtos = new ArrayList<>();
        for(Value value : template.getValues()) {
            valueDtos.add(ValueMapper.mapToValueDto(value));
        }

        templateDto.setValueDtos(valueDtos);
        return templateDto;
    }

    // Convert TemplateDto JPA Entity into Template

    public static Template mapToTemplate(TemplateDto templateDto) {
        Template template = new Template();
        template.setTemplateName(templateDto.getTemplateName());

        List<Value> values = new ArrayList<>();
        for (ValueDto valueDto : templateDto.getValueDtos()) {
            values.add(ValueMapper.mapToValue(valueDto));
        }

        template.setValues(values);
        template.setTimestamp(LocalDateTime.now());
        template.setServiceId(templateDto.getServiceId());
        template.setIsArchive(false);

        return template;
    }

}
