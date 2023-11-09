package project.rest_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.TemplateDto;
import project.dto.ValueDto;
import project.entity.Template;
import project.service.TemplateService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/templates")
public class TemplateRestControllerV1 {

    private final TemplateService templateService;

    @GetMapping
    public ResponseEntity<List<TemplateDto>> getAllTemplateDto() {
        return new ResponseEntity<>(templateService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/service/{serviceId}")
    public ResponseEntity<List<TemplateDto>> getAllTemplateDtoByServiceId(
            @PathVariable("serviceId") Long serviceId) {
        return new ResponseEntity<>(templateService.getAllByServiceId(serviceId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Template> createTemplate(@RequestBody @Valid TemplateDto templateDto) {
        return new ResponseEntity<>(templateService.createTemplate(templateDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{templateId}")
    public ResponseEntity<Template> updateTemplate(
            @PathVariable("templateId") Long templateId,
            @RequestBody @Valid TemplateDto templateDto) {
        return new ResponseEntity<>(templateService.updateTemplate(templateId, templateDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{templateId}")
    public ResponseEntity<String> deleteById(
            @PathVariable("templateId") Long templateId) {
        templateService.deleteById(templateId);
        return new ResponseEntity<>("Шаблон переведен в архив", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{templateId}/values_object")
    public ResponseEntity<List<ValueDto>> getAllValueByTemplateId(
            @PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(templateService.getAllValueByTemplate(templateId), HttpStatus.OK);
    }

    @GetMapping(value = "/{templateId}/create_values_object")
    public ResponseEntity<List<Object>> getAllCreateValueByTemplateId(
            @PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(templateService.getAllCreateValueByTemplate(templateId), HttpStatus.OK);
    }

    @GetMapping(value = "/{templateId}/update_values_object")
    public ResponseEntity<List<Object>> getAllUpdateValueByTemplateId(
            @PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(templateService.getAllUpdateValueByTemplate(templateId), HttpStatus.OK);
    }
}
