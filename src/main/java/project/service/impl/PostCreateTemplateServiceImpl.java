package project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.PostCreateTemplateDto;
import project.entity.PostCreateTemplate;
import project.entity.Template;
import project.exceptions.PostCreateTemplateNotFoundException;
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
@Slf4j
public class PostCreateTemplateServiceImpl implements PostCreateTemplateService {

    private final PostCreateTemplateRepository postCreateTemplateRepository;
    private final TemplateRepository templateRepository;

    @Override
    @Transactional
    @CachePut(cacheNames = {"createPostCreateTemplateCache"}, key = "#templateId")
    public PostCreateTemplate createPostCreateTemplate(Long templateId, PostCreateTemplateDto postCreateTemplateDto) {

        Optional<Template> template = templateRepository.findById(templateId);
        postCreateTemplateDto.setTemplate(template.orElseThrow(
                () ->  new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
        ));
        PostCreateTemplate postCreateTemplate = PostCreateTemplateMapper.mapToPostCreateTemplate(postCreateTemplateDto);
        log.info("createPostCreateTemplate postCreateTemplate : {} ", postCreateTemplate);
        return postCreateTemplateRepository.save(postCreateTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = {"createPostCreateTemplateCache"}, key = "#postCreateTemplateId")
    public Optional<PostCreateTemplateDto> getByIdPostCreateTemplate(Long postCreateTemplateId) {
        Optional<PostCreateTemplate> postCreateTemplate = postCreateTemplateRepository.findById(postCreateTemplateId);
        PostCreateTemplateDto postCreateTemplateDto =PostCreateTemplateMapper
                .mapToPostCreateTemplateDto(postCreateTemplate.orElseThrow(
                        () -> new PostCreateTemplateNotFoundException("Такого досоздание запроса для шаблона нет id : " + postCreateTemplateId)
                ));
        log.info("getByIdPostCreateTemplate postCreateTemplateDto : {} ", postCreateTemplateDto);
        return Optional.of(postCreateTemplateDto);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"createPostCreateTemplateCache"}, key = "#postCreateTemplateId")
    public void deleteByIdPostCreateTemplate(Long postCreateTemplateId) {
        Optional<PostCreateTemplate> postCreateTemplate = postCreateTemplateRepository.findById(postCreateTemplateId);
        if (postCreateTemplate.isEmpty()) {
            throw new PostCreateTemplateNotFoundException("Такого досоздание запроса для шаблона нет id : " + postCreateTemplateId);
        }
        log.info("deleteByIdPostCreateTemplate postCreateTemplate.setIsArchive(true): {} ", postCreateTemplate);
        postCreateTemplate.get().setIsArchive(true);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = {"createPostCreateTemplateCache"}, key = "#templateId")
    public List<Object> findAllByTemplateId(Long templateId) {
        List<Object> postCreateTemplateDtoList = new ArrayList<>();
        for (PostCreateTemplate postCreateTemplate : postCreateTemplateRepository.findAllByTemplateTemplateId(templateId)) {
            postCreateTemplateDtoList.add(ParseJson.parse(postCreateTemplate.getJsonValue()));
        }

        log.info("findAllByTemplateId postCreateTemplateDtoList: {} ", postCreateTemplateDtoList);
        return postCreateTemplateDtoList;
    }
}
