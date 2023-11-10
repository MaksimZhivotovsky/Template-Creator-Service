package project.service;

import project.dto.TemplateDto;
import project.entity.Template;

import java.util.List;
import java.util.Optional;

public interface TemplateService {

    List<TemplateDto> getAllTemplates();

    Optional<TemplateDto> getByTemplateId(Long templateId);

    Template createTemplate(TemplateDto valueDto);

    Template updateTemplate(Long valueId, TemplateDto valueDto);

    void deleteById(Long valueId);

    List<TemplateDto> getAllByServerId(Long serverId);

    String getCreateValue(Long templateId);

    String getUpdateValue(Long templateId);

    List<String> getAllCreateValueTemplateByServiceId(Long serviceId);

    List<String> getAllUpdateValueByServiceId(Long serviceId);

}
