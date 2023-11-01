package project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.JsonTemplateDto;
import project.dto.PostCreateTemplateDto;
import project.dto.TemplateDto;
import project.entity.JsonTemplate;
import project.entity.PostCreateTemplate;
import project.entity.Template;
import project.exceptions.TemplateNotFoundException;
import project.mapper.JsonTemplateMapper;
import project.mapper.PostCreateTemplateMapper;
import project.mapper.TemplateMapper;
import project.repository.JsonTemplateRepository;
import project.repository.PostCreateTemplateRepository;
import project.repository.TemplateRepository;
import project.service.TemplateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final JsonTemplateRepository jsonTemplateRepository;
    private final PostCreateTemplateRepository postCreateTemplateRepository;

    @Override
    public List<TemplateDto> getAllTemplates() {
        List<TemplateDto> templateDtoList = new ArrayList<>();
        for (Template template : templateRepository.findAll()) {
            templateDtoList.add(TemplateMapper.mapToTemplateDto(template));
        }

        return templateDtoList;
    }

    @Override
    public Optional<TemplateDto> getByIdTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        checkTemplate(templateId);
        TemplateDto templateDto = TemplateMapper.mapToTemplateDto(template.get());
        return Optional.of(templateDto);
    }

    @Override
    public Template createTemplate(TemplateDto templateDto) {
        Template template = TemplateMapper.mapToTemplate(templateDto);

        JsonTemplateDto jsonTemplateDto = new JsonTemplateDto();
        jsonTemplateDto.setTemplate(template);
        jsonTemplateDto.setJsonValue(templateDto.getJsonTemplate());

        PostCreateTemplateDto postCreateTemplateDto = new PostCreateTemplateDto();
        postCreateTemplateDto.setTemplate(template);
        postCreateTemplateDto.setJsonValue(templateDto.getPostCreateTemplate());

        template.setJsonTemplates(JsonTemplateMapper.mapToJsonTemplate(jsonTemplateDto));
        template.setPostCreateTemplates(PostCreateTemplateMapper.mapToPostCreateTemplate(postCreateTemplateDto));

        return templateRepository.save(template);
    }

    @Override
    public Template updateTemplate(Long templateId, TemplateDto templateDto) {
        Optional<Template> dataTemplate = templateRepository.findById(templateId);
        checkTemplate(templateId);
        if(templateDto.getName() != null) {
            dataTemplate.get().setName(templateDto.getName());
        }
        if(templateDto.getJsonTemplate() != null) {
            JsonTemplateDto jsonTemplateDto = new JsonTemplateDto();
            jsonTemplateDto.setTemplate(dataTemplate.get());
            jsonTemplateDto.setJsonValue(templateDto.getJsonTemplate());
            dataTemplate.get().setJsonTemplate(templateDto.getJsonTemplate());
            jsonTemplateRepository.save(JsonTemplateMapper.mapToJsonTemplate(jsonTemplateDto));
        }
        if(templateDto.getPostCreateTemplate() != null) {
            PostCreateTemplateDto postCreateTemplateDto = new PostCreateTemplateDto();
            postCreateTemplateDto.setTemplate(dataTemplate.get());
            postCreateTemplateDto.setJsonValue(templateDto.getPostCreateTemplate());
            dataTemplate.get().setPostCreateTemplate(templateDto.getPostCreateTemplate());
            postCreateTemplateRepository.save(PostCreateTemplateMapper.mapToPostCreateTemplate(postCreateTemplateDto));
        }

        return dataTemplate.get();
    }

    @Override
    public void deleteByIdTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        checkTemplate(templateId);
        template.get().setIsArchive(true);
        templateRepository.save(template.get());
    }

    private void checkTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        if(template.isEmpty()) {
            throw new TemplateNotFoundException("Такого шаблона нет id : " + templateId);
        }
    }
}
