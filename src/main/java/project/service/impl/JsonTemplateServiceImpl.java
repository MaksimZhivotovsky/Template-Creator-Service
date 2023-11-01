package project.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.JsonTemplateDto;
import project.entity.JsonTemplate;
import project.entity.Template;
import project.exceptions.TemplateNotFoundException;
import project.mapper.JsonTemplateMapper;
import project.repository.JsonTemplateRepository;
import project.repository.TemplateRepository;
import project.service.JsonTemplateService;
import project.utils.ObjectMapperUtil;
import project.utils.ParseJson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
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
    public void deleteByIdJsonTemplate(Long jsonTemplateId) {
        Optional<JsonTemplate> jsonTemplate = jsonTemplateRepository.findById(jsonTemplateId);
        if(jsonTemplate.isEmpty()) {
            throw new RuntimeException("Такого запроса для шаблона нет id : " + jsonTemplateId);
        }
        jsonTemplate.get().setIsArchive(true);
    }

    @Override
    public List<Object> getAllByTemplateId(Long templateId) {
        List<Object> jsonTemplateDtoList = new ArrayList<>();
        for(JsonTemplate jsonTemplate : jsonTemplateRepository.findAllByTemplateTemplateId(templateId)) {
            jsonTemplateDtoList.add(ParseJson.parse(jsonTemplate.getJsonValue()));
        }
        return jsonTemplateDtoList;
    }



    private void checkTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        if(template.isEmpty()) {
            throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
    }
}
