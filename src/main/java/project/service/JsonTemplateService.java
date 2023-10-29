package project.service;

import project.dto.JsonTemplateDto;
import project.entity.JsonTemplate;

import java.util.List;
import java.util.Optional;

public interface JsonTemplateService {

    JsonTemplate createJsonTemplate(Long templateId, JsonTemplateDto jsonTemplateDto);
    Optional<JsonTemplate> getByIdJsonTemplate(Long jsonTemplateId);
    void deleteByIdJsonTemplate(Long jsonTemplateId);
    List<JsonTemplate> findAllByTemplateId(Long templateId);
    JsonTemplate updateJsonTemplate(Long templateId, JsonTemplateDto jsonTemplateDto);
    Object getJsonTemplate(Long templateId);
}
