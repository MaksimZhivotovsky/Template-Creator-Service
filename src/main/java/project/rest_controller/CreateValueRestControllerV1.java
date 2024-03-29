package project.rest_controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.CreateValueDto;
import project.entity.CreateValue;
import project.service.CreateValueService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/values")
@Tag(name = "CreateValueRestControllerV1",
        description = "Работа с запросом value")
public class CreateValueRestControllerV1 {

    private final CreateValueService createValueService;

    @Operation(
            summary = "Получение истории всех изменений запроса шаблона",
            description = "Позволяет получить всю историю изменений конкретного шаблона"
    )
    @GetMapping(value = "/{valueId}/create_values")
    public ResponseEntity<List<Object>> getAllCreateValueByValue(
            @PathVariable("valueId") @Parameter(description = "ID идентификатор шаблона") Long valueId) {
        return new ResponseEntity<>(createValueService.getAllByValueId(valueId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение запроса создание шаблона по id",
            description = "Позволяет получить запроса создание шаблон по id"
    )
    @GetMapping(value = "/create_values/{createValueId}")
    public ResponseEntity<Optional<Object>> getByIdJsonTemplate(
            @PathVariable("createValueId") @Parameter(description = "ID идентификатор запроса value") Long createValueId) {
        return new ResponseEntity<>(createValueService.getByIdCreateValue(createValueId), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание запроса шаблона",
            description = "Позволяет создать запрос для шаблона"
    )
    @PostMapping(value = "/{valueId}/create_values")
    public ResponseEntity<CreateValue> createCreateValue(
            @PathVariable("valueId") @Parameter(description = "ID идентификатор value") Long valueId,
             @RequestBody @Parameter(description = "DTO запроса JSON для создания value") @Valid CreateValueDto createValueDto) {
        return new ResponseEntity<>(createValueService.createCreateValue(valueId, createValueDto), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Удаление запроса шаблона",
            description = "Позволяет удалить запрос шаблона(переводит в состояние поля isArchive = true)"
    )
    @DeleteMapping(value = "/create_values/{createValueId}")
    public ResponseEntity<String> deleteByIdCreateValue(
            @PathVariable("createValueId") @Parameter(description = "ID идентификатор запроса для создание шаблона") Long createValueId) {
        createValueService.deleteByIdCreateValue(createValueId);
        return new ResponseEntity<>("Строка CreateValue переведена в архив", HttpStatus.NO_CONTENT);
    }
}
