package project.rest_controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.ValueDto;
import project.entity.Value;
import project.service.ValueService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/values")
@Tag(name = "ValueRestControllerV1", description = "Работа с value")
public class ValueRestControllerV1 {

    private final ValueService valueService;

    @Operation(
            summary = "Получение всех value",
            description = "Позволяет получить все value"
    )
    @GetMapping
    public ResponseEntity<List<ValueDto>> getAllValuesByOrganizationId(@RequestParam("keycloakId") String keycloakId) {
        return new ResponseEntity<>(valueService.getAllValuesByOrganizationId(keycloakId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение шаблона по id",
            description = "Позволяет получить шаблон по id"
    )
    @GetMapping(value = "/{valueId}")
    public ResponseEntity<Optional<ValueDto>> getByIdValue(
            @RequestParam("keycloakId") String keycloakId,
            @PathVariable("valueId") @Parameter(description = "ID идентификатор шаблона") Long valueId) {
        return new ResponseEntity<>(valueService.getByIdValue(keycloakId, valueId), HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление шаблона",
            description = "Позволяет добавить шаблон в БД"
    )
    @PostMapping
    public ResponseEntity<Value> createValue(
            @RequestParam("keycloakId") String keycloakId,
            @RequestBody @Parameter(description = "DTO Шаблона") @Valid ValueDto valueDto) {
        return new ResponseEntity<>(valueService.createValue(keycloakId, valueDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление шаблона",
            description = "Позволяет обновить шаблон по id"
    )
    @PutMapping(value = "/{valueId}/{keycloakId}")
    public ResponseEntity<Value> updateValue(
            @PathVariable("valueId") @Parameter(description = "ID идентификатор шаблона") Long valueId,
            @PathVariable("keycloakId") @Parameter(description = "ID идентификатор шаблона") String keycloakId,
            @RequestBody @Parameter(description = "DTO Шаблона") @Valid ValueDto valueDto) {
        return new ResponseEntity<>(valueService.updateValue(keycloakId, valueId, valueDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление шаблона",
            description = "Позволяет удалить шаблон по id(переводит в состояние архивного)"
    )
    @DeleteMapping(value = "/{valueId}")
    public ResponseEntity<String> deleteByIdValue(
            @RequestParam("keycloakId") String keycloakId,
            @PathVariable("valueId") @Parameter(description = "ID идентификатор шаблона") Long valueId) {
        valueService.deleteByIdValue(keycloakId, valueId);
        return new ResponseEntity<>("Value переведена в архив", HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Получение всех value принадлежащих к сервису",
            description = "Позволяет получить все value принадлежащих к сервису"
    )
    @GetMapping(value = "/service/{serviceId}")
    public ResponseEntity<List<ValueDto>> getAllValueDtoByServiceId(
            @PathVariable("serviceId") @Parameter(description = "ID сервиса") Long serviceId) {
        return new ResponseEntity<>(valueService.getAllByServerId(serviceId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение всех JsonValue принадлежащих к организации",
            description = "Позволяет получить все JsonValue принадлежащих к организации"
    )
    @GetMapping(value = "/organization/{organizationId}/json_value")
    public ResponseEntity<List<String>> getAllUpdateValueByValue(
            @PathVariable("organizationId") @Parameter(description = "ID Value") Long organizationId) {
        return new ResponseEntity<>(valueService.getAllJsonValueByOrganization(organizationId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение всех UpdateValue принадлежащих к организации",
            description = "Позволяет получить все UpdateValue принадлежащих к организации"
    )
    @GetMapping(value = "/organization/{organizationId}/update_value")
    public ResponseEntity<List<String>> getAllCreateValueByValue(
            @PathVariable("organizationId") @Parameter(description = "ID Value") Long organizationId) {
        return new ResponseEntity<>(valueService.getAllUpdateValueByOrganizationId(organizationId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение всех JsonValue принадлежащих к Service",
            description = "Позволяет получить все CreateValue принадлежащих к Service "
    )
    @GetMapping(value = "/service/{serviceId}/json_values")
    public ResponseEntity<List<Object>> getAllCreateValueByServiceId(
            @PathVariable("serviceId") @Parameter(description = "ID сервиса") Long serviceId) {
        return new ResponseEntity<>(valueService.getAllJsonValueByServiceId(serviceId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение всех UpdateValue принадлежащих к Service",
            description = "Позволяет получить все UpdateValue принадлежащих к Service "
    )
    @GetMapping(value = "/service/{serviceId}/update_values")
    public ResponseEntity<List<Object>> getAllUpdateValueByServiceId(
            @PathVariable("serviceId") @Parameter(description = "ID сервиса") Long serviceId) {
        return new ResponseEntity<>(valueService.getAllUpdateValueByServiceId(serviceId), HttpStatus.OK);
    }
}
