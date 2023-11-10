package project.rest_controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.TemplateDto;
import project.entity.Template;
import project.service.TemplateService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/templates")
@Tag(name = "TemplateRestControllerV1", description = "Работа с Template")
public class TemplateRestControllerV1 {

    private final TemplateService templateService;

    @Operation(
            summary = "Получение всех Template",
            description = "Позволяет получить все Template"
    )
    @GetMapping
    public ResponseEntity<List<TemplateDto>> getAllTemplates() {
        return new ResponseEntity<>(templateService.getAllTemplates(), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение шаблона по id",
            description = "Позволяет получить шаблон по id"
    )
    @GetMapping(value = "/{templateId}")
    public ResponseEntity<Optional<TemplateDto>> getByIdTemplate(
            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId) {
        return new ResponseEntity<>(templateService.getByTemplateId(templateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление шаблона",
            description = "Позволяет добавить шаблон в БД"
    )
    @PostMapping
    public ResponseEntity<Template> createTemplate(
            @RequestBody @Parameter(description = "DTO Шаблона") @Valid TemplateDto valueDto) {
        return new ResponseEntity<>(templateService.createTemplate(valueDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление шаблона",
            description = "Позволяет обновить шаблон по id"
    )
    @PutMapping(value = "/{templateId}")
    public ResponseEntity<Template> updateTemplate(
            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId,
            @RequestBody @Parameter(description = "DTO Шаблона") @Valid TemplateDto templateDto) {
        return new ResponseEntity<>(templateService.updateTemplate(templateId, templateDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление шаблона",
            description = "Позволяет удалить шаблон по id(переводит в состояние архивного)"
    )
    @DeleteMapping(value = "/{templateId}")
    public ResponseEntity<String> deleteByIdTemplate(
            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId) {
        templateService.deleteById(templateId);
        return new ResponseEntity<>("Template переведен в архив", HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Получение всех Template принадлежащих к сервису",
            description = "Позволяет получить все Template принадлежащих к сервису"
    )
    @GetMapping(value = "/service/{serviceId}")
    public ResponseEntity<List<TemplateDto>> getAllTemplateDtoByServiceId(
            @PathVariable("serviceId") @Parameter(description = "ID сервиса") Long serviceId) {
        return new ResponseEntity<>(templateService.getAllByServerId(serviceId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение всех UpdateValue принадлежащих к Template",
            description = "Позволяет получить все UpdateValue принадлежащих к Template"
    )
    @GetMapping(value = "/{templateId}/update_value")
    public ResponseEntity<String> getUpdateValueByTemplate(
            @PathVariable("templateId") @Parameter(description = "ID Template") Long templateId) {
        return new ResponseEntity<>(templateService.getUpdateValue(templateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение всех CreateValue принадлежащих к Template",
            description = "Позволяет получить все CreateValue принадлежащих к Template"
    )
    @GetMapping(value = "/{templateId}/create_values")
    public ResponseEntity<String> getCreateValueByTemplate(
            @PathVariable("templateId") @Parameter(description = "ID Template") Long templateId) {
        return new ResponseEntity<>(templateService.getCreateValue(templateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение всех CreateValue принадлежащих к Service",
            description = "Позволяет получить все CreateValue принадлежащих к Service "
    )
    @GetMapping(value = "/service/{templateId}/objects_create_values")
    public ResponseEntity<List<String>> getAllCreateValueByServiceId(
            @PathVariable("templateId") @Parameter(description = "ID сервиса") Long templateId) {
        return new ResponseEntity<>(templateService.getAllCreateValueTemplateByServiceId(templateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение всех UpdateValue принадлежащих к Service",
            description = "Позволяет получить все UpdateValue принадлежащих к Service "
    )
    @GetMapping(value = "/service/{templateId}/objects_update_values")
    public ResponseEntity<List<String>> getAllUpdateValueByServiceId(
            @PathVariable("templateId") @Parameter(description = "ID сервиса") Long templateId) {
        return new ResponseEntity<>(templateService.getAllUpdateValueByServiceId(templateId), HttpStatus.OK);
    }
}
