package project.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import project.dto.JsonTemplateDto;
import project.entity.JsonTemplate;
import project.entity.Template;
import project.exceptions.TemplateNotFoundException;
import project.mapper.JsonTemplateMapper;
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
    public JsonTemplate createJsonTemplate(Long templateId, JsonTemplateDto jsonTemplateDto) {
        Optional<Template> template = templateRepository.findById(templateId);
        checkTemplate(templateId);
        jsonTemplateDto.setTemplate(template.get());
        JsonTemplate jsonTemplate = JsonTemplateMapper.mapToJsonTemplate(jsonTemplateDto);

        return jsonTemplateRepository.save(jsonTemplate);
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
    public JsonTemplate updateJsonTemplate(Long templateId, JsonTemplateDto jsonTemplateDto)  {
        checkTemplate(templateId);
        JsonTemplate jsonTemplate = JsonTemplateMapper.mapToJsonTemplate(jsonTemplateDto);
        return jsonTemplateRepository.save(jsonTemplate);
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
        JsonFactory factory = mapper.getFactory();
        JsonParser parser;
        try {
            parser = factory.createParser(parse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonNode actualObj;
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
