package project.rest_controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.JsonTemplateDto;
import project.entity.JsonTemplate;
import project.service.JsonTemplateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/templates/{templateId}/json_template")
@Tag(name="JsonTemplateRestControllerV1",
        description="Работа с запросом шаблона")
public class JsonTemplateRestControllerV1 {

    private final JsonTemplateService jsonTemplateService;

    @Operation(
            summary = "Получение истории всех изменений запроса шаблона",
            description = "Позволяет получить всю историю изменений конкретного шаблона"
    )
    @GetMapping
    public ResponseEntity<List<JsonTemplateDto>> getAllByTemplateIdJsonTemplate(
            @PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(jsonTemplateService.getAllByTemplateId(templateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание запроса шаблона",
            description = "Позволяет создать запрос для шаблона"
    )
    @PostMapping
    public ResponseEntity<JsonTemplate> createJsonTemplate(
            @PathVariable("templateId") Long templateId,
            @RequestBody JsonTemplateDto jsonTemplateDto) {
        return new ResponseEntity<>(jsonTemplateService.createJsonTemplate(templateId, jsonTemplateDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление запроса шаблона",
            description = "Позволяет обновить запрос шаблона"
    )
    @PutMapping()
    public ResponseEntity<JsonTemplate> updateJsonTemplate(
            @PathVariable("templateId") Long templateId,
            @RequestBody JsonTemplateDto jsonTemplateDto) {
        return new ResponseEntity<>(jsonTemplateService.updateJsonTemplate(templateId, jsonTemplateDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение последнего изменения запроса шаблона",
            description = "Позволяет получить всю историю изменений конкретного шаблона"
    )
    @GetMapping(value = "/last")
    public ResponseEntity<Object> getJsonTemplateLast(@PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(jsonTemplateService.getJsonTemplate(templateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление запроса шаблона",
            description = "Позволяет удалить запрос шаблона(переводит в состояние поля isArchive = true)"
    )
    @DeleteMapping(value = "/{jsonTemplateId}")
    public void deleteByIdJsonTemplate(@PathVariable("jsonTemplateId") Long jsonTemplateId) {
        jsonTemplateService.deleteByIdJsonTemplate(jsonTemplateId);
    }
}
