package project.rest_controller;

import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="TemplateRestControllerV1",
        description="Работа с шаблона")
public class TemplateRestControllerV1 {

    private final TemplateService templateService;

    @GetMapping
    public ResponseEntity<List<TemplateDto>> getAllTemplates() {
        return new ResponseEntity<>(templateService.getAllTemplates(), HttpStatus.OK);
    }

    @GetMapping(value = "/{templateId}")
    public ResponseEntity<Optional<TemplateDto>> getByIdTemplate(@PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(templateService.getByIdTemplate(templateId), HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<Template> createTemplate(@RequestBody TemplateDto templateDto) {
        return new ResponseEntity<>(templateService.createTemplate(templateDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{templateId}")
    public ResponseEntity<Template> updateTemplate(
            @PathVariable("templateId") Long templateId,
            @RequestBody TemplateDto templateDto) {
        return new ResponseEntity<>(templateService.updateTemplate(templateId, templateDto), HttpStatus.OK);
    }
    @DeleteMapping(value = "/{templateId}")
    public void deleteByIdTemplate(@PathVariable("templateId") Long templateId) {
        templateService.deleteByIdTemplate(templateId);
    }
}
