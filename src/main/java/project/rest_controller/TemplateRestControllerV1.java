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

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/templates")
@Tag(name="TemplateRestControllerV1", description="Работа с шаблона")
public class TemplateRestControllerV1 {

    private final TemplateService templateService;

    @Operation(
            summary = "Получение всех шаблонов",
            description = "Позволяет получить все шаблоны"
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
        return new ResponseEntity<>(templateService.getByIdTemplate(templateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление шаблона",
            description = "Позволяет добавить шаблон в БД"
    )
    @PostMapping
    public  ResponseEntity<Template> createTemplate(
            @RequestBody @Parameter(description = "DTO Шаблона") TemplateDto templateDto) {
        return new ResponseEntity<>(templateService.createTemplate(templateDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление шаблона",
            description = "Позволяет обновить шаблон по id"
    )
    @PutMapping(value = "/{templateId}")
    public ResponseEntity<Template> updateTemplate(
            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона")  Long templateId,
            @RequestBody @Parameter(description = "DTO Шаблона") TemplateDto templateDto) {
        return new ResponseEntity<>(templateService.updateTemplate(templateId, templateDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление шаблона",
            description = "Позволяет удалить шаблон по id(переводит в состояние архивного)"
    )
    @DeleteMapping(value = "/{templateId}")
    public void deleteByIdTemplate(
            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId) {
        templateService.deleteByIdTemplate(templateId);
    }
}
