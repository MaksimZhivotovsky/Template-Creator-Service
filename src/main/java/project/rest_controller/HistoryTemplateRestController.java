package project.rest_controller;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.entity.HistoryTemplate;
import project.entity.Template;
import project.service.HistoryTemplateService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/templates/{templateId}/history_template")
public class HistoryTemplateRestController {

    private final HistoryTemplateService historyTemplateService;

    @GetMapping
    public ResponseEntity<List<HistoryTemplate>> getAllByTemplateIdHistoryTemplate(
            @PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(historyTemplateService.findAllByTemplateId(templateId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HistoryTemplate> createHistoryTemplate(
            @PathVariable("templateId") Long templateId, @RequestBody HistoryTemplate historyTemplate) {
        return new ResponseEntity<>(historyTemplateService.createHistoryTemplate(templateId, historyTemplate), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{historyTemplateId}")
    public ResponseEntity<HistoryTemplate> updateHistoryTemplate(
            @PathVariable("templateId") Long templateId,
            @PathVariable("historyTemplateId") Long historyTemplateId,
            @RequestBody HistoryTemplate historyTemplate) {
        return new ResponseEntity<>(historyTemplateService.updateHistoryTemplate(templateId, historyTemplateId, historyTemplate), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<HistoryTemplate> updateHistoryTemplateJson(
            @PathVariable("templateId") Long templateId,
            @RequestBody Template template
    ) {
        return new ResponseEntity<>(historyTemplateService.updateHistoryTemplateJsonValue(templateId,template), HttpStatus.OK);
    }

    @GetMapping(value = "/last")
    public ResponseEntity<Object> getHistoryTemplateLast(@PathVariable("templateId") Long templateId) throws IOException {
        return new ResponseEntity<>(historyTemplateService.getHistoryTemplateActual(templateId), HttpStatus.OK);
    }


}
