package project.rest_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.JsonTemplateDto;
import project.entity.JsonTemplate;
import project.entity.Template;
import project.service.JsonTemplateService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/templates/{templateId}/json_template")
public class JsonTemplateRestController {

    private final JsonTemplateService jsonTemplateService;

    @GetMapping
    public ResponseEntity<List<JsonTemplate>> getAllByTemplateIdJsonTemplate(
            @PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(jsonTemplateService.findAllByTemplateId(templateId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JsonTemplate> createJsonTemplate(
            @PathVariable("templateId") Long templateId,
            @RequestBody JsonTemplateDto jsonTemplateDto) {
        return new ResponseEntity<>(jsonTemplateService.createJsonTemplate(templateId, jsonTemplateDto), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<JsonTemplate> updateJsonTemplate(
            @PathVariable("templateId") Long templateId,
            @RequestBody JsonTemplateDto jsonTemplateDto) {
        return new ResponseEntity<>(jsonTemplateService.updateJsonTemplate(templateId, jsonTemplateDto), HttpStatus.OK);
    }

    @GetMapping(value = "/last")
    public ResponseEntity<Object> getJsonTemplateLast(@PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(jsonTemplateService.getJsonTemplate(templateId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{jsonTemplateId}")
    public void deleteByIdJsonTemplate(@PathVariable("jsonTemplateId") Long jsonTemplateId) {
        jsonTemplateService.deleteByIdJsonTemplate(jsonTemplateId);
    }
}
