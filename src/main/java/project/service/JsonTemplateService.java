package project.service;

import project.dto.JsonTemplateDto;
import project.entity.JsonTemplate;

import java.util.List;
import java.util.Optional;

public interface JsonTemplateService {

    JsonTemplate createJsonTemplate(Long templateId, JsonTemplateDto jsonTemplateDto);

    void deleteByIdJsonTemplate(Long jsonTemplateId);

    List<Object> getAllByTemplateId(Long templateId);

    Optional<JsonTemplateDto> getByIdJsonTemplate(Long jsonTemplateId);

}
