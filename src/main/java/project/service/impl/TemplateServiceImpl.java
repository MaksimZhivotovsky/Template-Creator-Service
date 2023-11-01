package project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.TemplateDto;
import project.entity.Template;
import project.exceptions.TemplateNotFoundException;
import project.mapper.TemplateMapper;
import project.repository.TemplateRepository;
import project.service.TemplateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
    public List<TemplateDto> getAllTemplates() {
        List<TemplateDto> templateList = new ArrayList<>();
        for (Template template : templateRepository.findAll()) {
            templateList.add(TemplateMapper.mapToTemplateDto(template));
        }
        return templateList;
    }

    @Override
    public Optional<Template> getByIdTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        checkTemplate(templateId);
        return template;
    }

    @Override
    public Template createTemplate(TemplateDto templateDto) {
        Template template = TemplateMapper.mapToTemplate(templateDto);

        return templateRepository.save(template);
    }

    @Override
    public Template updateTemplate(Long templateId, TemplateDto templateDto) {
        Optional<Template> dataTemplate = templateRepository.findById(templateId);
        checkTemplate(templateId);
        if(templateDto.getName() != null) {
            dataTemplate.get().setName(templateDto.getName());
        }
//        if(templateDto.getJsonTemplate() != null) {
//            dataTemplate.get().setJsonTemplate(dataTemplate.get().getJsonTemplate());
//        }

        return dataTemplate.get();
    }

    @Override
    public void deleteByIdTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        checkTemplate(templateId);
        template.get().setIsArchive(true);
        templateRepository.save(template.get());
    }

    private void checkTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        if(template.isEmpty()) {
            throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
    }
}
