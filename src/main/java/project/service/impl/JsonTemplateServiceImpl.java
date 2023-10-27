package project.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import project.entity.JsonTemplate;
import project.entity.Template;
import project.exceptions.TemplateNotFoundException;
import project.repository.JsonTemplateRepository;
import project.repository.TemplateRepository;
import project.service.JsonTemplateService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JsonTemplateServiceImpl implements JsonTemplateService {

    private final JsonTemplateRepository jsonTemplateRepository;
    private final TemplateRepository templateRepository;

    @Override
    public JsonTemplate createJsonTemplate(Long templateId, JsonTemplate jsonTemplate) {
        Optional<Template> template = templateRepository.findById(templateId);
        checkTemplate(templateId);
        jsonTemplate.setTemplate(template.get());
        return jsonTemplateRepository.save(jsonTemplate);
    }

    @Override
    public JsonTemplate updateJsYonTemplate(Long templateId, Long historyTemplateId, JsonTemplate jsonTemplate) {
        checkTemplate(templateId);
        Optional<JsonTemplate> rData = jsonTemplateRepository.findById(historyTemplateId);
        checkTemplate(templateId);
        if(jsonTemplate.getJsonValue() != null) {
            rData.get().setJsonValue(rData.get().getJsonValue());
        }
        if(jsonTemplate.getIsArchive() != null) {
            rData.get().setIsArchive(jsonTemplate.getIsArchive());
        }
        if(jsonTemplate.getTemplate() != null) {
            rData.get().setTemplate(jsonTemplate.getTemplate());
        }
        return rData.get();
    }

    @Override
    public Optional<JsonTemplate> getByIdJsonTemplate(Long jsonTemplateId) {
        return jsonTemplateRepository.findById(jsonTemplateId);
    }

    @Override
    public void deleteByIdJsonTemplate(Long jsonTemplateId) {
        Optional<JsonTemplate> requestData = jsonTemplateRepository.findById(jsonTemplateId);
        if(requestData.isEmpty()) {
            throw new RuntimeException("Такого запроса для шаблона нет id : " + jsonTemplateId);
        }
        requestData.get().setIsArchive(true);
    }

    @Override
    public List<JsonTemplate> findAllByTemplateId(Long templateId) {
        return jsonTemplateRepository.findAllByTemplateTemplateId(templateId);
    }

    @Override
    public JsonTemplate updateJsonTemplate(Long templateId, Template template)  {
        Optional<Template> rData = templateRepository.findById(templateId);
        checkTemplate(templateId);
        JsonTemplate requestData = new JsonTemplate();
        if(template != null) {
            requestData.setTemplate(rData.get());
            requestData.setJsonValue(template.getJsonTemplate());
        }
        return jsonTemplateRepository.save(requestData);
    }

    @Override
    public Object getJsonTemplate(Long templateId) {
        Optional<JsonTemplate> historyTemplate =
                jsonTemplateRepository.findFirstByTemplateTemplateIdOrderByTimestampDesc(templateId);

        if(historyTemplate.isEmpty()) {
            Optional<Template> template = templateRepository.findById(templateId);
            return parserJson(template.get().getJsonTemplate());
        }
        return parserJson(historyTemplate.get().getJsonValue());

    }

    private Object parserJson(String parse) {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getJsonFactory();
        JsonParser parser = null;
        try {
            parser = factory.createJsonParser(parse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonNode actualObj = null;
        try {
            actualObj = mapper.readTree(parser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return actualObj;
    }

    private void checkTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        if(template.isEmpty()) {
            throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
    }
}
