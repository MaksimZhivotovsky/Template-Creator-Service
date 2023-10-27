package project.service;

import org.json.JSONObject;
import project.entity.HistoryTemplate;
import project.entity.Template;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface HistoryTemplateService {

    HistoryTemplate createHistoryTemplate(Long templateId, HistoryTemplate historyTemplate);
    HistoryTemplate updateHistoryTemplate(Long templateId, Long requestDataId, HistoryTemplate historyTemplate);
    Optional<HistoryTemplate> getByIdHistoryTemplate(Long requestDataId);
    void deleteByIdHistoryTemplate(Long historyTemplateId);
    List<HistoryTemplate> findAllByTemplateId(Long templateId);
    HistoryTemplate updateHistoryTemplateJsonValue(Long templateId, Template RequestData);
    Object getHistoryTemplateActual(Long templateId) throws IOException;
}
