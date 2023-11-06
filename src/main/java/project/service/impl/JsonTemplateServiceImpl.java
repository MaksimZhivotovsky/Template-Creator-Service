package project.service.impl;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.JsonTemplateDto;
import project.entity.JsonTemplate;
import project.entity.Template;
import project.exceptions.JsonTemplateNotFoundException;
import project.exceptions.TemplateNotFoundException;
import project.mapper.JsonTemplateMapper;
import project.repository.JsonTemplateRepository;
import project.repository.TemplateRepository;
import project.service.JsonTemplateService;
import project.utils.ParseJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JsonTemplateServiceImpl implements JsonTemplateService {

    private final JsonTemplateRepository jsonTemplateRepository;
    private final TemplateRepository templateRepository;

    @Override
    @Transactional
    @CachePut(cacheNames = {"createJsonTemplateCache"}, key = "#templateId")
    public JsonTemplate createJsonTemplate(Long templateId, JsonTemplateDto jsonTemplateDto) {
        Optional<Template> template = templateRepository.findById(templateId);
        jsonTemplateDto.setTemplate(template.orElseThrow(
                () ->  new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
        ));
        JsonTemplate jsonTemplate = JsonTemplateMapper.mapToJsonTemplate(jsonTemplateDto);

        log.info("createJsonTemplate jsonTemplate : {} ", jsonTemplate);
        return jsonTemplateRepository.save(jsonTemplate);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"createJsonTemplateCache"}, key = "#jsonTemplateId")
    public void deleteByIdJsonTemplate(Long jsonTemplateId) {
        Optional<JsonTemplate> jsonTemplate = jsonTemplateRepository.findById(jsonTemplateId);
        if (jsonTemplate.isEmpty()) {
            throw new JsonTemplateNotFoundException("Такого запроса для шаблона нет id : " + jsonTemplateId);
        }
        log.info("deleteByIdJsonTemplate jsonTemplate.setIsArchive(true) : {} ", jsonTemplate);
        jsonTemplate.get().setIsArchive(true);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = {"createJsonTemplateCache"}, key = "#jsonTemplateId")
    public Optional<JsonTemplateDto> getByIdJsonTemplate(Long jsonTemplateId) {
        Optional<JsonTemplate> jsonTemplate = jsonTemplateRepository.findById(jsonTemplateId);
        JsonTemplateDto jsonTemplateDto = JsonTemplateMapper.mapToJsonTemplateDto(jsonTemplate.orElseThrow(
                () -> new JsonTemplateNotFoundException("Такого запроса для шаблона нет id : " + jsonTemplateId)
        ));

        log.info("getByIdJsonTemplate jsonTemplateDto : {} ", jsonTemplateDto);
        return Optional.of(jsonTemplateDto);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = {"createJsonTemplateCache"}, key = "#templateId")
    public List<Object> getAllByTemplateId(Long templateId) {
        List<Object> jsonTemplateDtoList = new ArrayList<>();
        for (JsonTemplate jsonTemplate : jsonTemplateRepository.findAllByTemplateTemplateId(templateId)) {
            jsonTemplateDtoList.add(ParseJson.parse(jsonTemplate.getJsonValue()));
        }
        log.info("getAllByTemplateId jsonTemplateDtoList : {} ", jsonTemplateDtoList);
        return jsonTemplateDtoList;
    }
}
