package project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.dto.TemplateDto;
import project.dto.ValueDto;
import project.entity.Template;
import project.entity.Value;
import project.exceptions.TemplateNotFoundException;
import project.mapper.TemplateMapper;
import project.mapper.ValueMapper;
import project.repository.TemplateRepository;
import project.service.TemplateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
//    @Cacheable(value="templates", key="#root.method.name")
    public List<TemplateDto> getAll() {
        List<Template> templates = templateRepository.findAll();

        List<TemplateDto> templateDtoList = templates.stream()
                .map(TemplateMapper::mapToTemplateDto)
                .collect(Collectors.toList());
        log.info("getAllByServiceId {}", templateDtoList);
        return templateDtoList;
    }

    @Override
//    @Cacheable(value="templates", key="#serviceId")
    public List<TemplateDto> getAllByServiceId(Long serviceId) {
        List<Template> templates = templateRepository.findAllByServiceId(serviceId);
        List<TemplateDto> templateDtoList = templates.stream()
                .map(TemplateMapper::mapToTemplateDto)
                .collect(Collectors.toList());
        log.info("getAllByServiceId {}", templateDtoList);
        return templateDtoList;
    }

    @Override
//    @CachePut(value="templates", key="#templateDto")
    public Template createTemplate(TemplateDto templateDto) {
        Template template = TemplateMapper.mapToTemplate(templateDto);
        Optional<Template> templateName = templateRepository.findByTemplateName(templateDto.getTemplateName());
        if (templateName.isPresent()) {
            throw new TemplateNotFoundException("templateName уже есть :" + templateDto.getTemplateName());
        }
        log.info("createTemplate {}", template);
        return templateRepository.save(template);
    }

    @Override
//    @CachePut(value="templates", key="#templateId")
    public Template updateTemplate(Long templateId, TemplateDto templateDto) {
        Optional<Template> templateData = templateRepository.findById(templateId);
        if (templateData.isEmpty()) {
            throw new TemplateNotFoundException("Шаблона нет с таким id : " + templateId);
        }

        Optional<Template> templateName = templateRepository.findByTemplateName(templateDto.getTemplateName());
        if (templateName.isPresent()
                && !templateName.get().getTemplateName().equals(templateData.get().getTemplateName())) {
            throw new TemplateNotFoundException("templateName уже есть : " + templateDto.getTemplateName());
        }

        if (templateDto.getTemplateName() != null) {
            templateData.get().setTemplateName(templateDto.getTemplateName());
        }

        if (templateDto.getServiceId() != null) {
            templateData.get().setServiceId(templateDto.getServiceId());
        }

        if (templateDto.getValueDtoList() != null) {
            List<Value> values = new ArrayList<>();
            for (ValueDto valueDto : templateDto.getValueDtoList()) {
                values.add(ValueMapper.mapToValue(valueDto));
            }
            templateData.get().setValues(values);
        }
        log.info("updateTemplate {}", templateData);
        return templateRepository.save(templateData.get());
    }

    @Override
//    @CacheEvict(cacheNames = {"templates"}, key = "#templateId")
    public void deleteById(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        template.orElseThrow(
                () -> new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
        ).setIsArchive(true);
        log.info("deleteById {}", template);
        templateRepository.save(template.get());
    }

    @Override
    public List<ValueDto> getAllValueByTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        List<ValueDto> valueDtoList = template.orElseThrow(
                        () -> new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
                ).getValues().stream()
                .map(ValueMapper::mapToValueDto)
                .collect(Collectors.toList());

        log.info("getAllValueByTemplate {}", valueDtoList);
        return valueDtoList;
    }

    @Override
    public List<Object> getAllCreateValueByTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        List<Object> createValueObject = template.orElseThrow(
                        () -> new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
                ).getValues().stream()
                .map(Value::getCreateValue)
                .collect(Collectors.toList());
        log.info("getAllCreateValueByTemplate {}", createValueObject);
        return createValueObject;
    }

    @Override
    public List<Object> getAllUpdateValueByTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        List<Object> updateValueObject = template.orElseThrow(
                        () -> new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
                ).getValues().stream()
                .map(Value::getUpdateValue)
                .collect(Collectors.toList());
        log.info("getAllUpdateValueByTemplate {}", updateValueObject);
        return updateValueObject;
    }
}
