package project.rest_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.TemplateDto;
import project.entity.Template;
import project.service.TemplateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Template> createTemplate(@RequestBody TemplateDto templateDto) {
        return new ResponseEntity<>(templateService.createTemplate(templateDto), HttpStatus.CREATED);
    }
}
