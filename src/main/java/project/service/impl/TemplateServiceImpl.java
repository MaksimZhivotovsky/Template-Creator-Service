package project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.TemplateDto;
import project.entity.HistoryTemplate;
import project.entity.Template;
import project.exceptions.TemplateNotFoundException;
import project.repository.HistoryTemplateRepository;
import project.repository.TemplateRepository;
import project.service.TemplateService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final HistoryTemplateRepository historyTemplateRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    @Override
    public Optional<Template> getByIdTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
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
    public Template updateTemplate(Long templateId, TemplateDto template) {
        Optional<Template> dataTemplate = templateRepository.findById(templateId);
        if(dataTemplate.isEmpty()) {
            throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
        if(template.getName() != null) {
            dataTemplate.get().setName(template.getName());
        }
        if(template.getJsonValue() != null) {
            dataTemplate.get().setJsonValue(dataTemplate.get().getJsonValue());
        }

        return dataTemplate.get();
    }

    @Override
    public void deleteByIdTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        if(template.isEmpty()) {
            throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
        template.get().setIsArchive(true);
    }
}
