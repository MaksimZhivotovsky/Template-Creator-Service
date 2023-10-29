package project.rest_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.PostCreateTemplateDto;
import project.entity.PostCreateTemplate;
import project.service.PostCreateTemplateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/templates/{templateId}/post_create_template")
public class PostCreateTemplateRestController {

    private final PostCreateTemplateService postCreateTemplateService;

    @GetMapping
    public ResponseEntity<List<PostCreateTemplate>> getAllByTemplateIdPostCreateTemplate(
            @PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(postCreateTemplateService.findAllByTemplateId(templateId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostCreateTemplate> createPostCreateTemplate(
            @PathVariable("templateId") Long templateId,
            @RequestBody PostCreateTemplateDto postCreateTemplateDto) {
        return new ResponseEntity<>(postCreateTemplateService.createPostCreateTemplate(
                templateId, postCreateTemplateDto), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PostCreateTemplate> updatePostCreateTemplate(
            @PathVariable("templateId") Long templateId,
            @RequestBody PostCreateTemplateDto postCreateTemplateDto) {
        return new ResponseEntity<>(postCreateTemplateService.updatePostCreateTemplate(
                templateId, postCreateTemplateDto), HttpStatus.OK);
    }

    @GetMapping(value = "/last")
    public ResponseEntity<Object> getPostCreateTemplateLast(
            @PathVariable("templateId") Long templateId) {
        return new ResponseEntity<>(postCreateTemplateService.getPostCreateTemplate(
                templateId), HttpStatus.OK);
    }

}
