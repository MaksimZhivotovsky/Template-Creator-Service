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
import project.utils.CheckTemplate;
import project.utils.ObjectMapperUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        CheckTemplate.checkTemplate(template);
        TemplateDto templateDto = TemplateMapper.mapToTemplateDto(template.get());
        return Optional.of(templateDto);
    }

    @Override
    public Template createTemplate(TemplateDto templateDto) {
        Optional<Template> findName = templateRepository.findByName(templateDto.getName());
        if(findName.isPresent()) {
            throw new TemplateNotFoundException("Шаблон с таким именем уже существует : " + templateDto.getName());
        }
        Template template = TemplateMapper.mapToTemplate(templateDto);
        if(template.getJsonTemplate() != null) {
            JsonTemplateDto jsonTemplateDto = new JsonTemplateDto();
            jsonTemplateDto.setTemplate(template);
            jsonTemplateDto.setJsonValue(templateDto.getJsonTemplate());
            template.setJsonTemplates(JsonTemplateMapper.mapToJsonTemplate(jsonTemplateDto));
        }

        if(template.getPostCreateTemplate() != null) {
            PostCreateTemplateDto postCreateTemplateDto = new PostCreateTemplateDto();
            postCreateTemplateDto.setTemplate(template);
            postCreateTemplateDto.setJsonValue(templateDto.getPostCreateTemplate());
            template.setPostCreateTemplates(PostCreateTemplateMapper.mapToPostCreateTemplate(postCreateTemplateDto));
        }

        return templateRepository.save(template);
    }

    @Override
    public Template updateTemplate(Long templateId, TemplateDto templateDto) {
        Optional<Template> dataTemplate = templateRepository.findById(templateId);
        CheckTemplate.checkTemplate(dataTemplate);
        TemplateDto check = TemplateMapper.mapToTemplateDto(dataTemplate.get());
        if(check.equals(templateDto)) {
            throw new TemplateNotFoundException("Такой шаблон уже существует : " + templateDto);
        }
        if(templateDto.getName() != null) {
            dataTemplate.get().setName(templateDto.getName());
        }

        List<String> findJsonTemplates = dataTemplate.get().getJsonTemplates().stream()
                .map(JsonTemplate::getJsonValue)
                .collect(Collectors.toList());

        if(templateDto.getJsonTemplate() != null
                && !findJsonTemplates.contains(ObjectMapperUtil.setValue(templateDto.getJsonTemplate()))) {
            JsonTemplateDto jsonTemplateDto = new JsonTemplateDto();
            jsonTemplateDto.setTemplate(dataTemplate.get());
            jsonTemplateDto.setJsonValue(templateDto.getJsonTemplate());
            jsonTemplateRepository.save(JsonTemplateMapper.mapToJsonTemplate(jsonTemplateDto));
        }

        List<String> findPostCreateTemplate = dataTemplate.get().getPostCreateTemplates().stream()
                .map(PostCreateTemplate::getJsonValue)
                .collect(Collectors.toList());

        if(templateDto.getPostCreateTemplate() != null
                && !findPostCreateTemplate.contains(ObjectMapperUtil.setValue(templateDto.getPostCreateTemplate()))) {
            PostCreateTemplateDto postCreateTemplateDto = new PostCreateTemplateDto();
            postCreateTemplateDto.setTemplate(dataTemplate.get());
            postCreateTemplateDto.setJsonValue(templateDto.getPostCreateTemplate());
            postCreateTemplateRepository.save(PostCreateTemplateMapper.mapToPostCreateTemplate(postCreateTemplateDto));
        }

        dataTemplate.get().setPostCreateTemplate(templateDto.getPostCreateTemplate());
        dataTemplate.get().setJsonTemplate(templateDto.getJsonTemplate());

        return dataTemplate.get();
    }

    @Override
    public void deleteByIdTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        CheckTemplate.checkTemplate(template);
        template.get().setIsArchive(true);
        templateRepository.save(template.get());
    }
}
