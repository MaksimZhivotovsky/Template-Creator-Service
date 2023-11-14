package project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.dto.TemplateDto;
import project.dto.UserRcDto;
import project.dto.ValueDto;
import project.entity.Template;
import project.entity.Value;
import project.exceptions.TemplateNotFoundException;
import project.mapper.TemplateMapper;
import project.mapper.ValueMapper;
import project.repository.TemplateRepository;
import project.repository.UserRcSQLRepository;
import project.service.TemplateService;
import project.utils.CheckUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRcSQLRepository userRcSQLRepository;


    //    @Cacheable(value="templates", key="#root.method.name")
    public List<Template> getAllByOrganisationId( Long organizationId) {
        List<Template> templates = templateRepository.findAllByOrganizationId(organizationId);

        log.info("getAllByServiceId {}", templates);
        return templates;
    }

    @Override
//    @CachePut(value="templates", key="#templateDto")
    public Template createTemplate(String keycloakId, TemplateDto templateDto) {
        Template template = TemplateMapper.mapToTemplate(templateDto);
        Optional<Template> templateName = templateRepository.findByName(templateDto.getName());
        if (templateName.isPresent()) {
            throw new TemplateNotFoundException("templateName уже есть :" + templateDto.getName());
        }
        UserRcDto userRcDto = userRcSQLRepository.findUserByKcId(keycloakId);
        template.setModifyData(LocalDateTime.now());
        template.setOrganizationId(userRcDto.getOrganizationId());
        log.info("createTemplate {}", template);
        return templateRepository.save(template);
    }

    @Override
//    @CachePut(value="templates", key="#templateId")
    public Template updateTemplate(String keycloakId, Long templateId, TemplateDto templateDto) {
        UserRcDto userRcDto = userRcSQLRepository.findUserByKcId(keycloakId);
        List<Template> templateList = templateRepository.findAllByOrganizationId(userRcDto.getOrganizationId());

        Optional<Template> template = templateList.stream()
                        .filter(t -> t.getId().equals(templateId))
                        .findFirst();
        template.orElseThrow(
                () -> new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
        ).setOrganizationId(userRcDto.getOrganizationId());

        List<String> nameList = templateList.stream()
                .map(Template::getName)
                .collect(Collectors.toList());
        if(nameList.contains(templateDto.getName())) {
            throw new TemplateNotFoundException("templateName уже есть : " + templateDto.getName());
        }

        if (templateDto.getName() != null) {
            template.get().setName(templateDto.getName());
        }

        if (templateDto.getValues() != null) {
            List<Value> values = new ArrayList<>();
            for (ValueDto valueDto : templateDto.getValues()) {
                values.add(ValueMapper.mapToValue(valueDto));
            }
            template.get().setValues(values);
        }
        template.get().setModifyData(LocalDateTime.now());
        log.info("updateTemplate {}", template);
        return templateRepository.save(template.get());
    }

    @Override
//    @CacheEvict(cacheNames = {"templates"}, key = "#templateId")
    public void deleteById(String keycloakId, Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        CheckUser.check(keycloakId, template.orElseThrow(
                () -> new TemplateNotFoundException("У этой организации нет такого шаблона id : " + templateId)
        ).getOrganizationId());
        template.orElseThrow().setIsArchive(true);
        log.info("deleteById {}", template);
        templateRepository.save(template.get());
    }

    @Override
    public List<ValueDto> getAllValueByTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        List<ValueDto> valueDtoList = template.orElseThrow(
                        () -> new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
                ).getValues().stream()
                .map(ValueMapper::mapToValueDto)
                .collect(Collectors.toList());

        log.info("getAllValueByTemplate {}", valueDtoList);
        return valueDtoList;
    }

    @Override
    public List<Object> getAllCreateValueByTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        List<Object> createValueObject = template.orElseThrow(
                        () -> new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
                ).getValues().stream()
                .map(Value::getJsonValue)
                .collect(Collectors.toList());
        log.info("getAllCreateValueByTemplate {}", createValueObject);
        return createValueObject;
    }

    @Override
    public List<Object> getAllUpdateValueByTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        List<Object> updateValueObject = template.orElseThrow(
                        () -> new TemplateNotFoundException("Такого шаблона нет id : " + templateId)
                ).getValues().stream()
                .map(Value::getUpdateValue)
                .collect(Collectors.toList());
        log.info("getAllUpdateValueByTemplate {}", updateValueObject);
        return updateValueObject;
    }

}
