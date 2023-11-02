package project.service;

import project.dto.JsonTemplateDto;
import project.dto.PostCreateTemplateDto;
import project.entity.JsonTemplate;
import project.entity.PostCreateTemplate;

import java.util.List;
import java.util.Optional;

public interface PostCreateTemplateService {
    PostCreateTemplate createPostCreateTemplate(Long templateId, PostCreateTemplateDto postCreateTemplateDto);
    Optional<PostCreateTemplate> getByIdPostCreateTemplate(Long postCreateTemplateId);
    void deleteByIdPostCreateTemplate(Long postCreateTemplateId);
    List<Object> findAllByTemplateId(Long templateId);
}
