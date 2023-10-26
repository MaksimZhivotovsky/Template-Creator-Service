package project.service;

import project.dto.TemplateDto;
import project.entity.Template;

import java.util.List;
import java.util.Optional;

public interface TemplateService {

    List<Template> getAllTemplates();
    Optional<Template> getByIdTemplate(Long templateId);
    Template createTemplate(Template template);
    Template updateTemplate(Long templateId, TemplateDto template);
    void deleteByIdTemplate(Long templateId);
}
