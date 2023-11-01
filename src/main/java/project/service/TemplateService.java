package project.service;

import project.dto.TemplateDto;
import project.entity.Template;

import java.util.List;
import java.util.Optional;

public interface TemplateService {

    List<TemplateDto> getAllTemplates();
    Optional<TemplateDto> getByIdTemplate(Long templateId);
    Template createTemplate(TemplateDto template);
    Template updateTemplate(Long templateId, TemplateDto template);
    void deleteByIdTemplate(Long templateId);
}
