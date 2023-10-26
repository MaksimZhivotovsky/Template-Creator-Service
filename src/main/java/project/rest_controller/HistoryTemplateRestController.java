package project.rest_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.entity.HistoryTemplate;
import project.entity.Template;
import project.service.HistoryTemplateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/templates/{templateId}/history_template")
public class HistoryTemplateRestController {

    private final HistoryTemplateService requestDataService;

    @GetMapping
    public ResponseEntity<List<HistoryTemplate>> getAllByTemplateIdHistoryTemplate(
            @PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(requestDataService.findAllByTemplateId(templateId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HistoryTemplate> createHistoryTemplate(
            @PathVariable("templateId") Long templateId, @RequestBody HistoryTemplate historyTemplate) {
        return new ResponseEntity<>(requestDataService.createHistoryTemplate(templateId, historyTemplate), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{historyTemplateId}")
    public ResponseEntity<HistoryTemplate> updateHistoryTemplate(
            @PathVariable("templateId") Long templateId,
            @PathVariable("historyTemplateId") Long historyTemplateId,
            @RequestBody HistoryTemplate requestData) {
        return new ResponseEntity<>(requestDataService.updateHistoryTemplate(templateId, historyTemplateId, requestData), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<HistoryTemplate> updateHistoryTemplateJson(
            @PathVariable("templateId") Long templateId,
            @RequestBody Template template
    ) {
        return new ResponseEntity<>(requestDataService.updateHistoryTemplateJsonValue(templateId,template), HttpStatus.OK);
    }

    @GetMapping(value = "/last")
    public ResponseEntity<String> getHistoryTemplateLast(@PathVariable("templateId") Long templateId){
        return new ResponseEntity<>(requestDataService.getHistoryTemplateActual(templateId), HttpStatus.OK);
    }


}
