package project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import project.entity.JsonTemplate;
import project.entity.Template;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface JsonTemplateService {

    JsonTemplate createJsonTemplate(Long templateId, JsonTemplate jsonTemplate);
    JsonTemplate updateJsYonTemplate(Long templateId, Long requestDataId, JsonTemplate historyTemplate) throws JsonProcessingException;
    Optional<JsonTemplate> getByIdJsonTemplate(Long jsonTemplateId);
    void deleteByIdJsonTemplate(Long jsonTemplateId);
    List<JsonTemplate> findAllByTemplateId(Long templateId);
    JsonTemplate updateJsonTemplate(Long templateId, Template template);
    Object getJsonTemplate(Long templateId);
}
