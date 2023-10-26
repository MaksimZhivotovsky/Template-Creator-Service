package project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.entity.HistoryTemplate;
import project.entity.Template;
import project.exceptions.TemplateNotFoundException;
import project.repository.HistoryTemplateRepository;
import project.repository.TemplateRepository;
import project.service.HistoryTemplateService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryTemplateServiceImpl implements HistoryTemplateService {

    private final HistoryTemplateRepository historyTemplateRepository;
    private final TemplateRepository templateRepository;

    @Override
    public HistoryTemplate createHistoryTemplate(Long templateId, HistoryTemplate historyTemplate) {
        Optional<Template> template = templateRepository.findById(templateId);
        if(template.isEmpty()) {
            throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
        historyTemplate.setTemplate(template.get());
        return historyTemplateRepository.save(historyTemplate);
    }

    @Override
    public HistoryTemplate updateHistoryTemplate(Long templateId, Long historyTemplateId, HistoryTemplate historyTemplate) {
        Optional<Template> template = templateRepository.findById(templateId);
        if(template.isEmpty()) {
            throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
        Optional<HistoryTemplate> rData = historyTemplateRepository.findById(historyTemplateId);
        if(rData.isEmpty()) {
            throw new RuntimeException("Такого запроса для шаблона нет id : " + historyTemplateId);
        }
        if(historyTemplate.getHistoryJsonValue() != null) {
            rData.get().setHistoryJsonValue(rData.get().getHistoryJsonValue());
        }
        if(historyTemplate.getIsArchive() != null) {
            rData.get().setIsArchive(historyTemplate.getIsArchive());
        }
        if(historyTemplate.getTemplate() != null) {
            rData.get().setTemplate(historyTemplate.getTemplate());
        }
        return rData.get();
    }

    @Override
    public Optional<HistoryTemplate> getByIdHistoryTemplate(Long historyTemplateId) {
        return historyTemplateRepository.findById(historyTemplateId);
    }

    @Override
    public void deleteByIdHistoryTemplate(Long requestDataId) {
        Optional<HistoryTemplate> requestData = historyTemplateRepository.findById(requestDataId);
        if(requestData.isEmpty()) {
            throw new RuntimeException("Такого запроса для шаблона нет id : " + requestDataId);
        }
        requestData.get().setIsArchive(true);
    }

    @Override
    public List<HistoryTemplate> findAllByTemplateId(Long templateId) {
        return historyTemplateRepository.findAllByTemplateTemplateId(templateId);
    }

    @Override
    public HistoryTemplate updateHistoryTemplateJsonValue(Long templateId, Template template) {
        Optional<Template> rData = templateRepository.findById(templateId);
        if(rData.isEmpty()) {
            throw new RuntimeException("Такого запроса для шаблона нет id : " + templateId);
        }
        HistoryTemplate requestData = new HistoryTemplate();
        if(template != null) {
            requestData.setTemplate(rData.get());
            requestData.setHistoryJsonValue(template.getJsonValue());
        }
        return historyTemplateRepository.save(requestData);
    }

    @Override
    public String getHistoryTemplateActual(Long templateId) {
        Optional<HistoryTemplate> requestData = historyTemplateRepository.findFirstByTemplateTemplateIdOrderByTimestampDesc(templateId);
        return requestData.get().getHistoryJsonValue();
    }
}
