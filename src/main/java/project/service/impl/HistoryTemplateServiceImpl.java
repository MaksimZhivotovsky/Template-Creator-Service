package project.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import project.entity.HistoryTemplate;
import project.entity.Template;
import project.exceptions.TemplateNotFoundException;
import project.repository.HistoryTemplateRepository;
import project.repository.TemplateRepository;
import project.service.HistoryTemplateService;

import java.io.IOException;
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
            requestData.setHistoryJsonValue(template.getJsonValue().toString());
        }
        return historyTemplateRepository.save(requestData);
    }

    @Override
    public Object getHistoryTemplateActual(Long templateId) throws IOException {
        Optional<HistoryTemplate> historyTemplate =
                historyTemplateRepository.findFirstByTemplateTemplateIdOrderByTimestampDesc(templateId);

        if(historyTemplate.isEmpty()) {
            Optional<Template> template = templateRepository.findById(templateId);
//            JsonParser parser = new JsonParser();
//            //Creating JSONObject from String using parser
//            JsonObject JSONObject = parser.parse(template.get().getJsonValue()).getAsJsonObject();
//            JSONObject jsonObject = new JSONObject(template.get().getJsonValue());
            System.out.println(template.get().getJsonValue());

            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getJsonFactory();
            JsonParser parser = factory.createJsonParser(template.get().getJsonValue());
            JsonNode actualObj = mapper.readTree(parser);

//            return template.get().getJsonValue();
            return actualObj;
        }
        JSONObject jsonObject = new JSONObject(historyTemplate.get().getHistoryJsonValue());
        return jsonObject;
        //        return requestData.get().getHistoryJsonValue();

    }
}
