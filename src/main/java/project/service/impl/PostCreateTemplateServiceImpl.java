package project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.PostCreateTemplateDto;
import project.entity.PostCreateTemplate;
import project.entity.Template;
import project.exceptions.TemplateNotFoundException;
import project.mapper.PostCreateTemplateMapper;
import project.repository.PostCreateTemplateRepository;
import project.repository.TemplateRepository;
import project.service.PostCreateTemplateService;
import project.utils.ParseJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCreateTemplateServiceImpl implements PostCreateTemplateService {

    private final PostCreateTemplateRepository postCreateTemplateRepository;
    private final TemplateRepository templateRepository;

    @Override
    public PostCreateTemplate createPostCreateTemplate(Long templateId, PostCreateTemplateDto postCreateTemplateDto) {

        Optional<Template> template = templateRepository.findById(templateId);
        checkTemplate(templateId);
        postCreateTemplateDto.setTemplate(template.get());
        PostCreateTemplate postCreateTemplate = PostCreateTemplateMapper.mapToPostCreateTemplate(postCreateTemplateDto);
        return postCreateTemplateRepository.save(postCreateTemplate);
    }

    @Override
    public Optional<PostCreateTemplate> getByIdPostCreateTemplate(Long postCreateTemplateId) {
        return postCreateTemplateRepository.findById(postCreateTemplateId);
    }

    @Override
    public void deleteByIdPostCreateTemplate(Long postCreateTemplateId) {
        Optional<PostCreateTemplate> postCreateTemplate = postCreateTemplateRepository.findById(postCreateTemplateId);
        if(postCreateTemplate.isEmpty()) {
            throw new RuntimeException("Такого досоздание запроса для шаблона нет id : " + postCreateTemplateId);
        }
        postCreateTemplate.get().setIsArchive(true);
    }

    @Override
    public List<Object> findAllByTemplateId(Long templateId) {
        List<Object> postCreateTemplateDtoList = new ArrayList<>();
        for (PostCreateTemplate postCreateTemplate : postCreateTemplateRepository.findAllByTemplateTemplateId(templateId)){
            postCreateTemplateDtoList.add(ParseJson.parse(postCreateTemplate.getJsonValue()));
        }

        return postCreateTemplateDtoList;
    }

    private void checkTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        if(template.isEmpty()) {
            throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
    }
}
