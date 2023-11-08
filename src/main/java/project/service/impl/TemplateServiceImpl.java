package project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.dto.TemplateDto;
import project.entity.Template;
import project.entity.Value;
import project.mapper.TemplateMapper;
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
            throw new RuntimeException("templateName уже есть");
        }

//        List<Value> values = valueRepository.findAllByServiceId(template.getServiceId());

//        template.setValues(values);

        return templateRepository.save(template);
    }

    @Override
    public Template updateTemplate(Long templateId, TemplateDto templateDto) {
        Optional<Template> template = templateRepository.findById(templateId);

//        List<String> templateNames =
//
//        if ()

        if(templateDto.getTemplateName() != null) {
            template.get().setTemplateName(templateDto.getTemplateName());
        }
        if(templateDto.getServiceId() != null) {
            template.get().setServiceId(templateDto.getServiceId());
        }
//        if (!templateDto.getValues().isEmpty()) {
//            template.get().setValues(templateDto.getValues());
//        }

        return templateRepository.save(template.get());
    }

    @Override
    public void deleteById(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        template.get().setIsArchive(true);
        templateRepository.save(template.get());
    }
}
