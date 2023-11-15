package project.rest_controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "TemplateRestControllerV1", description = "Работа с Template")
public class TemplateRestControllerV1 {

    private final TemplateService templateService;

    @Operation(
            summary = "Получение всех Template принадлежащих к организации",
            description = "Позволяет получить все Template принадлежащих к организации"
    )
    @GetMapping(value = "/organization/{organizationId}")
    public ResponseEntity<List<Template>> getAllTemplateDtoByOrganizationId(
            @PathVariable("organizationId") @Parameter(description = "ID идентификатор организации") Long organizationId) {
        return new ResponseEntity<>(templateService.getAllByOrganisationId(organizationId), HttpStatus.OK);
    }

    @Operation(
            summary = "Сохранение Template принадлежащий к конкретной организации",
            description = "Позволяет сохранить Template принадлежащих к конкретной организации"
    )
    @PostMapping
    public ResponseEntity<Template> createTemplate(
//            @RequestParam("keycloakId") String keycloakId,
            @RequestHeader("keycloakId") @Parameter(description = "ID идентификатор keycloak") String keycloakId,
            @RequestBody @Valid @Parameter(description = "Dto шаблона") TemplateDto templateDto) {
        return new ResponseEntity<>(templateService.createTemplate(keycloakId, templateDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление Template принадлежащий к конкретной организации",
            description = "Позволяет обновить Template принадлежащих к конкретной организации"
    )
    @PutMapping(value = "/{templateId}")
    public ResponseEntity<Template> updateTemplate(
//            @RequestParam("keycloakId") String keycloakId,
            @RequestHeader("keycloakId") @Parameter(description = "ID идентификатор keycloak") String keycloakId,
            @PathVariable("templateId") @Parameter(description = "ID идентификатор Template") Long templateId,
            @RequestBody @Valid @Parameter(description = "Dto шаблона") TemplateDto templateDto) {
        return new ResponseEntity<>(templateService.updateTemplate(keycloakId, templateId, templateDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление Template принадлежащий к конкретной организации",
            description = "Позволяет удалить(перевести в архив) Template принадлежащих к конкретной организации"
    )
    @DeleteMapping(value = "/{templateId}")
    public ResponseEntity<String> deleteById(
//            @RequestParam("keycloakId") String keycloakId,
            @RequestHeader("keycloakId") @Parameter(description = "ID идентификатор keycloak") String keycloakId,
            @PathVariable("templateId") @Parameter(description = "ID идентификатор Template") Long templateId) {
        templateService.deleteById(keycloakId, templateId);
        return new ResponseEntity<>("Шаблон переведен в архив", HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Получить все Value принадлежащие Template",
            description = "Позволяет получить все Value принадлежащие Template "
    )
    @GetMapping(value = "/{templateId}/value_object")
    public ResponseEntity<List<ValueDto>> getAllValueByTemplateId(
            @PathVariable("templateId") @Parameter(description = "ID идентификатор Template") Long templateId) {
        return new ResponseEntity<>(templateService.getAllValueByTemplate(templateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить все JsonValue принадлежащие Template",
            description = "Позволяет получить все JsonValue принадлежащие Template "
    )
    @GetMapping(value = "/{templateId}/json_values")
    public ResponseEntity<List<String>> getAllJsonValueByTemplateId(
            @PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(templateService.getAllJsonValueByTemplate(templateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить все UpdateValue принадлежащие Template",
            description = "Позволяет получить все UpdateValue принадлежащие Template "
    )
    @GetMapping(value = "/{templateId}/update_values")
    public ResponseEntity<List<String>> getAllUpdateValueByTemplateId(
            @PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(templateService.getAllUpdateValueByTemplate(templateId), HttpStatus.OK);
    }
}
