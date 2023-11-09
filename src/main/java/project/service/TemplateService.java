package project.service;

import project.dto.TemplateDto;
import project.dto.ValueDto;
import project.entity.Template;

import java.util.List;

public interface TemplateService {

    List<TemplateDto> getAll();

    List<TemplateDto> getAllByServiceId(Long serviceId);

    Template createTemplate(TemplateDto templateDto);

    Template updateTemplate(Long templateId, TemplateDto templateDto);

    void deleteById(Long templateId);

    List<ValueDto> getAllValueByTemplate(Long templateId);

    List<Object> getAllCreateValueByTemplate(Long templateId);

    List<Object> getAllUpdateValueByTemplate(Long templateId);
}
