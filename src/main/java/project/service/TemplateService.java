package project.service;

import project.dto.TemplateDto;
import project.dto.ValueDto;
import project.entity.Template;

import java.util.List;

public interface TemplateService {

    List<TemplateDto> getAllByOrganisationId(Long keycloakId, Long organizationId);

    List<TemplateDto> getAllByServiceId(Long serviceId);

    Template createTemplate(Long keycloakId, TemplateDto templateDto);

    Template updateTemplate(Long keycloakId, Long templateId, TemplateDto templateDto);

    void deleteById(Long keycloakId, Long templateId);

    List<ValueDto> getAllValueByTemplate(Long templateId);

    List<Object> getAllCreateValueByTemplate(Long templateId);

    List<Object> getAllUpdateValueByTemplate(Long templateId);
}
