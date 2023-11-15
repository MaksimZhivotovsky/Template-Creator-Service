package project.service;

import project.dto.TemplateDto;
import project.dto.ValueDto;
import project.entity.Template;

import java.util.List;

public interface TemplateService {

    List<Template> getAllByOrganisationId(Long organizationId);

    Template createTemplate(String keycloakId, TemplateDto templateDto);

    Template updateTemplate(String keycloakId, Long templateId, TemplateDto templateDto);

    void deleteById(String keycloakId, Long templateId);

    List<ValueDto> getAllValueByTemplate(Long templateId);

    List<String> getAllJsonValueByTemplate(Long templateId);

    List<String> getAllUpdateValueByTemplate(Long templateId);
}
