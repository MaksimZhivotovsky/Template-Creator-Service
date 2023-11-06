package project.rest_controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.JsonTemplateDto;
import project.entity.JsonTemplate;
import project.service.JsonTemplateService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/templates/{templateId}/json_templates")
@Tag(name = "JsonTemplateRestControllerV1",
        description = "Работа с запросом шаблона")
public class JsonTemplateRestControllerV1 {

    private final JsonTemplateService jsonTemplateService;

    @Operation(
            summary = "Получение истории всех изменений запроса шаблона",
            description = "Позволяет получить всю историю изменений конкретного шаблона"
    )
    @GetMapping
    public ResponseEntity<List<Object>> getAllByTemplateIdJsonTemplate(
            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId) {
        return new ResponseEntity<>(jsonTemplateService.getAllByTemplateId(templateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение запроса создание шаблона по id",
            description = "Позволяет получить запроса создание шаблон по id"
    )
    @GetMapping(value = "/{jsonTemplateId}")
    public ResponseEntity<Optional<JsonTemplateDto>> getByIdJsonTemplate(
//            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId,
            @PathVariable("jsonTemplateId") @Parameter(description = "ID идентификатор запроса шаблона") Long jsonTemplateId) {
        return new ResponseEntity<>(jsonTemplateService.getByIdJsonTemplate(jsonTemplateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание запроса шаблона",
            description = "Позволяет создать запрос для шаблона"
    )
    @PostMapping
    public ResponseEntity<JsonTemplate> createJsonTemplate(
            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId,
             @RequestBody @Parameter(description = "DTO запроса JSON для создания шаблона")@Valid JsonTemplateDto jsonTemplateDto) {
        return new ResponseEntity<>(jsonTemplateService.createJsonTemplate(templateId, jsonTemplateDto), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Удаление запроса шаблона",
            description = "Позволяет удалить запрос шаблона(переводит в состояние поля isArchive = true)"
    )
    @DeleteMapping(value = "/{jsonTemplateId}")
    public void deleteByIdJsonTemplate(
//            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId,
            @PathVariable("jsonTemplateId") @Parameter(description = "ID идентификатор запроса для создание шаблона") Long jsonTemplateId) {
        jsonTemplateService.deleteByIdJsonTemplate(jsonTemplateId);
    }
}
