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
    public ResponseEntity<List<ValueDto>> getAllValues() {
        return new ResponseEntity<>(valueService.getAllValues(), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение шаблона по id",
            description = "Позволяет получить шаблон по id"
    )
    @GetMapping(value = "/{valueId}")
    public ResponseEntity<Optional<ValueDto>> getByIdValue(
            @PathVariable("valueId") @Parameter(description = "ID идентификатор шаблона") Long valueId) {
        return new ResponseEntity<>(valueService.getByIdValue(valueId), HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление шаблона",
            description = "Позволяет добавить шаблон в БД"
    )
    @PostMapping
    public ResponseEntity<Value> createValue(
             @RequestBody @Parameter(description = "DTO Шаблона") @Valid ValueDto valueDto) {
        return new ResponseEntity<>(valueService.createValue(valueDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление шаблона",
            description = "Позволяет обновить шаблон по id"
    )
    @PutMapping(value = "/{valueId}")
    public ResponseEntity<Value> updateValue(
            @PathVariable("valueId") @Parameter(description = "ID идентификатор шаблона") Long valueId,
            @RequestBody @Parameter(description = "DTO Шаблона") @Valid ValueDto valueDto) {
        return new ResponseEntity<>(valueService.updateValue(valueId, valueDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление шаблона",
            description = "Позволяет удалить шаблон по id(переводит в состояние архивного)"
    )
    @DeleteMapping(value = "/{valueId}")
    public void deleteByIdValue(
            @PathVariable("valueId") @Parameter(description = "ID идентификатор шаблона") Long valueId) {
        valueService.deleteByIdValue(valueId);
    }
}
