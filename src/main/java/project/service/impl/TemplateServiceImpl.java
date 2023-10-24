package project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.entity.Template;
import project.exceptions.TemplateNotFoundException;
import project.repository.TemplateRepository;
import project.service.TemplateService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    @Override
    public Optional<Template> getByIdTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findAll().stream()
                .filter(temple -> temple.getTemplateId().equals(templateId))
                .findAny();
        if(template.isEmpty()) {
           throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
        return template;
    }

    @Override
    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }

    @Override
    public Template updateTemplate(Long templateId, Template template) {
        Optional<Template> dataTemplate = templateRepository.findAll().stream()
                .filter(temple -> temple.getTemplateId().equals(templateId))
                .findAny();
        if(dataTemplate.isEmpty()) {
            throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
        if(template.getName() != null) {
            dataTemplate.get().setName(template.getName());
        }
        if(template.getSqlValue() != null) {
            dataTemplate.get().setSqlValue(template.getSqlValue());
        }
        if(template.getIsArchive() != null) {
            dataTemplate.get().setIsArchive(template.getIsArchive());
        }

        return dataTemplate.get();
    }

    @Override
    public void deleteByIdTemplate(Long templateId) {
        templateRepository.deleteById(templateId);
    }
}
