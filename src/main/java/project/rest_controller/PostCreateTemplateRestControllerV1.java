package project.rest_controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.PostCreateTemplateDto;
import project.entity.PostCreateTemplate;
import project.service.PostCreateTemplateService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/templates/{templateId}/post_create_templates")
@Tag(name = "PostCreateTemplateRestControllerV1",
        description = "Работа с запросом до создание шаблона")
public class PostCreateTemplateRestControllerV1 {

    private final PostCreateTemplateService postCreateTemplateService;

    @Operation(
            summary = "Получение истории всех изменений до создание запроса шаблона",
            description = "Позволяет получить всю историю изменений конкретного шаблона"
    )
    @GetMapping
    public ResponseEntity<List<Object>> getAllByTemplateIdPostCreateTemplate(
            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId) {
        return new ResponseEntity<>(postCreateTemplateService.findAllByTemplateId(templateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание запроса для до создание шаблона",
            description = "Позволяет создать запрос для до создания шаблона"
    )
    @PostMapping
    public ResponseEntity<PostCreateTemplate> createPostCreateTemplate(
            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId,
             @RequestBody @Parameter(description = "DTO запроса JSON для до создания шаблона")@Valid PostCreateTemplateDto postCreateTemplateDto) {
        return new ResponseEntity<>(postCreateTemplateService.createPostCreateTemplate(
                templateId, postCreateTemplateDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Удаление запроса для до создание шаблона",
            description = "Позволяет удалить запрос для до создания шаблона(переводит в состояние поля isArchive = true)"
    )
    @DeleteMapping(value = "/{postCreateTemplateId}")
    public void deleteByIdPostCreateTemplate(
            @PathVariable("postCreateTemplateId")
            @Parameter(description = "ID идентификатор запроса для до создания запроса") Long postCreateTemplateId) {
        postCreateTemplateService.deleteByIdPostCreateTemplate(postCreateTemplateId);
    }

    @Operation(
            summary = "Получение запроса для до создание шаблона по id",
            description = "Позволяет получить запрос для до создание шаблон по id"
    )
    @GetMapping(value = "/{postCreateTemplateId}")
    public ResponseEntity<Optional<PostCreateTemplateDto>> getByIdPostCreateTemplate(
//            @PathVariable("templateId") @Parameter(description = "ID идентификатор шаблона") Long templateId,
            @PathVariable("postCreateTemplateId") @Parameter(description = "ID идентификатор запроса для до создония шаблона") Long postCreateTemplateId) {
        return new ResponseEntity<>(postCreateTemplateService.getByIdPostCreateTemplate(postCreateTemplateId), HttpStatus.OK);
    }
}
