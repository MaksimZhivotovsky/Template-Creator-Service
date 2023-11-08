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
import project.repository.ValueRepository;
import project.service.TemplateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateServiceImpl implements TemplateService{

    private final TemplateRepository templateRepository;
    private final ValueRepository valueRepository;

    @Override
    public List<TemplateDto> getAll() {
        List<TemplateDto> templateDtoList = new ArrayList<>();

        for (Template template : templateRepository.findAll()) {
            templateDtoList.add(TemplateMapper.mapToTemplateDto(template));
        }
        log.info("getAllByServiceId {}", templateDtoList);
        return templateDtoList;
    }

    @Override
    public List<TemplateDto> getAllByServiceId(Long serviceId) {
        List<TemplateDto> templateDtoList = new ArrayList<>();

        for (Template template : templateRepository.findAllByServiceId(serviceId)) {
            templateDtoList.add(TemplateMapper.mapToTemplateDto(template));
        }

        log.info("getAllByServiceId {}", templateDtoList);
        return templateDtoList;
    }

    @Override
    public Template createTemplate(TemplateDto templateDto) {
        Template template = TemplateMapper.mapToTemplate(templateDto);
        Optional<Template> templateName = templateRepository.findByTemplateName(templateDto.getTemplateName());
        if (templateName.isPresent()) {
            throw new TemplateNotFoundException("templateName уже есть :" + templateDto.getTemplateName());
        }
        return templateRepository.save(template);
    }

    @Override
    public Template updateTemplate(Long templateId, TemplateDto templateDto) {
        Optional<Template> templateData = templateRepository.findById(templateId);
        if(templateData.isEmpty()) {
            throw new TemplateNotFoundException("Шаблона нет с таким id : " + templateId);
        }

        Optional<Template> templateName = templateRepository.findByTemplateName(templateDto.getTemplateName());
        if(templateName.isPresent()
                && !templateName.get().getTemplateName().equals(templateData.get().getTemplateName())) {
            throw new TemplateNotFoundException("templateName уже есть : " + templateDto.getTemplateName());
        }

        if (templateDto.getTemplateName() != null) {
            templateData.get().setTemplateName(templateDto.getTemplateName());
        }

        if (templateDto.getServiceId() != null) {
            templateData.get().setServiceId(templateDto.getServiceId());
        }

        if (templateDto.getValueDtos() != null) {
            List<Value> values = new ArrayList<>();
            for(ValueDto valueDto : templateDto.getValueDtos()) {
                values.add(ValueMapper.mapToValue(valueDto));
            }
            templateData.get().setValues(values);
        }

        return templateRepository.save(templateData.get());
    }

    @Override
    public void deleteById(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        template.orElseThrow(
                () -> new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
        ).setIsArchive(true);
        templateRepository.save(template.get());
    }
}
