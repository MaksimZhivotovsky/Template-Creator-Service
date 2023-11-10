package project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.TemplateDto;
import project.entity.Template;
import project.exceptions.TemplateNotFoundException;
import project.mapper.TemplateMapper;
import project.repository.TemplateRepository;
import project.service.TemplateService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TemplateDto> getAllTemplates() {
        List<Template> values = templateRepository.findAll();
        log.info("getAllTemplates value : {} ", values);

        return values.stream()
                .map(TemplateMapper::mapToValueDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = {"valueCache"}, key = "#templateId")
    public Optional<TemplateDto> getByTemplateId(Long templateId) {
        Optional<Template> value = templateRepository.findById(templateId);
        TemplateDto valueDto = TemplateMapper.mapToValueDto(value.orElseThrow(
                () -> new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
        ));
        log.info("getByIdTemplate valueDto : {} ", valueDto);
        return Optional.of(valueDto);
    }

    @Override
    @Transactional
//    @CachePut(cacheNames = {"valueCache"}, key = "#valueDto")
    public Template createTemplate(TemplateDto valueDto) {
        Template template = TemplateMapper.mapToValue(valueDto);
        List<Template> templateList = templateRepository.findAll();

        List<String> createValue = templateList.stream()
                .map(Template::getCreateValue)
                .collect(Collectors.toList());

        if (createValue.contains(valueDto.getCreateValue())) {
            throw new TemplateNotFoundException("template уже есть : " + valueDto.getCreateValue());
        }

        log.info("createTemplate template : {} ", template);
        return templateRepository.save(template);
    }

    @Override
    @Transactional
//    @CachePut(cacheNames = {"valueCache"}, key = "#valueDto")
    public Template updateTemplate(Long valueId, TemplateDto templateDto) {
        Optional<Template> template = templateRepository.findById(valueId);
        List<Template> templateList = templateRepository.findAll();

        for(Template template1 : templateList) {
            if(templateDto.getCreateValue() != null
                    && template1.getCreateValue().equals(templateDto.getCreateValue())) {
                throw new TemplateNotFoundException("CreateValue");
            }
            if(templateDto.getUpdateValue() != null
                    && template1.getUpdateValue().equals(templateDto.getUpdateValue())) {
                throw new TemplateNotFoundException("UpdateValue");
            }
        }

        template.get().setCreateValue(templateDto.getUpdateValue());
        template.get().setUpdateValue(templateDto.getCreateValue());

        template.get().setModifyData(LocalDateTime.now());
        log.info("updateTemplate template : {} ", template.get());

        return template.get();
    }

    @Override
    @Transactional
//    @CacheEvict(cacheNames = {"valueCache"}, key = "#templateId")
    public void deleteById(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        template.orElseThrow(
                () -> new TemplateNotFoundException("Такого Template нет id : " + templateId)
        ).setIsArchive(true);
        log.info("deleteByIdTemplate template.setIsArchive(true) : {} ", template.get());
        templateRepository.save(template.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemplateDto> getAllByServerId(Long serverId) {
        List<Template> templateList = templateRepository.findAllByServiceId(serverId);
        log.info("getAllByServerId templateList : {} ", templateList);

        return templateList.stream()
                .map(TemplateMapper::mapToValueDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public String getUpdateValue(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        String createValue = template.orElseThrow(
                () -> new TemplateNotFoundException("Такого Value нет id : " + templateId)
        ).getCreateValue();
        log.info("getAllUpdateValueDtoByValue template : {} ", createValue);
        return createValue;
    }

    @Override
    @Transactional(readOnly = true)
    public String getCreateValue(Long templateId) {
        Optional<Template> value = templateRepository.findById(templateId);
        String updateValue = value.orElseThrow(
                () -> new TemplateNotFoundException("Такого Value нет id : " + templateId)
        ).getUpdateValue();
        log.info("getAllCreateValueDtoByValue value : {} ", updateValue);
        return updateValue;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCreateValueTemplateByServiceId(Long serviceId) {
        List<Template> values = templateRepository.findAllByServiceId(serviceId);

        List<String> createValue = values.stream()
                .map(Template::getCreateValue)
                .collect(Collectors.toList());
        log.info("getAllCreateValueByServiceId createValue : {} ", createValue);

        return createValue;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllUpdateValueByServiceId(Long serviceId) {
        List<Template> values = templateRepository.findAllByServiceId(serviceId);

        List<String> updateValue = values.stream()
                .map(Template::getUpdateValue)
                .collect(Collectors.toList());
        log.info("getAllCreateValueByServiceId updateValue : {} ", updateValue);

        return updateValue;
    }
}
