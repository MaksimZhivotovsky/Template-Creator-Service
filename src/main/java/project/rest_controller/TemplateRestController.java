package project.rest_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.TemplateDto;
import project.entity.Template;
import project.service.TemplateService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/templates")
public class TemplateRestController {

    private final TemplateService templateService;

    @GetMapping
    public ResponseEntity<List<Template>> getAllTemplates() {
        return new ResponseEntity<>(templateService.getAllTemplates(), HttpStatus.OK);
    }

    @GetMapping(value = "/{templateId}")
    public ResponseEntity<Optional<Template>> getByIdTemplate(@PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(templateService.getByIdTemplate(templateId), HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<Template> createTemplate(@RequestBody Template template) {
        return new ResponseEntity<>(templateService.createTemplate(template), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{templateId}")
    public ResponseEntity<Template> updateTemplate(
            @PathVariable("templateId") Long templateId,
            @RequestBody TemplateDto template) {
        return new ResponseEntity<>(templateService.updateTemplate(templateId, template), HttpStatus.OK);
    }
    @DeleteMapping(value = "/{templateId}")
    public void deleteByIdTemplate(@PathVariable("templateId") Long templateId) {
        templateService.deleteByIdTemplate(templateId);
    }
}
