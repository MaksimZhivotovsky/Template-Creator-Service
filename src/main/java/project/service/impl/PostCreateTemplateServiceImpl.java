package project.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
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
    }

    @Override
    public List<PostCreateTemplate> findAllByTemplateId(Long templateId) {
        return postCreateTemplateRepository.findAllByTemplateTemplateId(templateId);
    }

    @Override
    public PostCreateTemplate updatePostCreateTemplate(Long templateId, PostCreateTemplateDto postCreateTemplateDto) {
        Optional<Template> template = templateRepository.findById(templateId);
        checkTemplate(templateId);
        PostCreateTemplate postCreateTemplate =
                PostCreateTemplateMapper.mapToPostCreateTemplate(postCreateTemplateDto);
        postCreateTemplate.setTemplate(template.get());
        return postCreateTemplateRepository.save(postCreateTemplate);
    }

    @Override
    public Object getPostCreateTemplate(Long templateId) {

        Optional<PostCreateTemplate>  postCreateTemplate =
                postCreateTemplateRepository.findFirstByTemplateTemplateIdOrderByTimestampDesc(templateId);
        if(postCreateTemplate.isEmpty()) {
            Optional<Template> template = templateRepository.findById(templateId);
            return parserJson(template.get().getJsonTemplate());
        }
        return parserJson(postCreateTemplate.get().getJsonValue());
    }

    private Object parserJson(String parse) {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser;
        try {
            parser = factory.createParser(parse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonNode actualObj;
        try {
            actualObj = mapper.readTree(parser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return actualObj;
    }

    private void checkTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        if(template.isEmpty()) {
            throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
    }
}
