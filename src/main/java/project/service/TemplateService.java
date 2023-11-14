package project.service;

import project.dto.TemplateDto;
import project.dto.ValueDto;
import project.entity.Template;

import java.util.List;

public interface TemplateService {

    List<TemplateDto> getAllByOrganisationId(String keycloakId, Long organizationId);

//    List<TemplateDto> getAllByServiceId(Long serviceId);

    Template createTemplate(String keycloakId, TemplateDto templateDto);

    Template updateTemplate(String keycloakId, Long templateId, TemplateDto templateDto);

    void deleteById(String keycloakId, Long templateId);

    List<ValueDto> getAllValueByTemplate(Long templateId);

    List<Object> getAllCreateValueByTemplate(Long templateId);

    List<Object> getAllUpdateValueByTemplate(Long templateId);
}
