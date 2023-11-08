package project.rest_controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.UpdateValueDto;
import project.entity.UpdateValue;
import project.service.UpdateValueService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/values/{valueId}/update_values")
@Tag(name = "UpdateValueRestControllerV1",
        description = "Работа с запросом до создание шаблона")
public class UpdateValueRestControllerV1 {

    private final UpdateValueService updateValueService;

    @Operation(
            summary = "Получение истории всех изменений до создание запроса шаблона",
            description = "Позволяет получить всю историю изменений конкретного шаблона"
    )
    @GetMapping
    public ResponseEntity<List<Object>> getAllUpdateValueByValue(
            @PathVariable("valueId") @Parameter(description = "ID идентификатор шаблона") Long valueId) {
        return new ResponseEntity<>(updateValueService.findAllByValueId(valueId), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание запроса для до создание шаблона",
            description = "Позволяет создать запрос для до создания шаблона"
    )
    @PostMapping
    public ResponseEntity<UpdateValue> createUpdateValue(
            @PathVariable("valueId") @Parameter(description = "ID идентификатор шаблона") Long valueId,
             @RequestBody @Parameter(description = "DTO запроса JSON для до создания шаблона")@Valid UpdateValueDto updateValueDto) {
        return new ResponseEntity<>(updateValueService.createUpdateValue(
                valueId, updateValueDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Удаление запроса для до создание шаблона",
            description = "Позволяет удалить запрос для до создания шаблона(переводит в состояние поля isArchive = true)"
    )
    @DeleteMapping(value = "/{updateValueId}")
    public ResponseEntity<String> deleteByIdPostCreateTemplate(
            @PathVariable("updateValueId")
            @Parameter(description = "ID идентификатор запроса для до создания запроса") Long updateValueId) {
        updateValueService.deleteByIdUpdateValue(updateValueId);
        return new ResponseEntity<>("Строка UpdateValue переведена в архив", HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Получение запроса для до создание шаблона по id",
            description = "Позволяет получить запрос для до создание шаблон по id"
    )
    @GetMapping(value = "/{updateValueId}")
    public ResponseEntity<Optional<Object>> getByIdPostCreateTemplate(
//            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId,
            @PathVariable("updateValueId") @Parameter(description = "ID идентификатор запроса для до создония шаблона") Long updateValueId) {
        return new ResponseEntity<>(updateValueService.getByIdUpdateValue(updateValueId), HttpStatus.OK);
    }
}
