package project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.CreateValueDto;
import project.dto.UpdateValueDto;
import project.dto.ValueDto;
import project.entity.CreateValue;
import project.entity.UpdateValue;
import project.entity.Value;
import project.exceptions.ValueNotFoundException;
import project.mapper.CreateValueMapper;
import project.mapper.UpdateValueMapper;
import project.mapper.ValueMapper;
import project.repository.CreateValueRepository;
import project.repository.UpdateValueRepository;
import project.repository.ValueRepository;
import project.service.ValueService;
import project.utils.ObjectMapperUtil;
import project.utils.ParseJson;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValueServiceImpl implements ValueService {

    private final ValueRepository valueRepository;
    private final CreateValueRepository createValueRepository;
    private final UpdateValueRepository updateValueRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ValueDto> getAllValues() {
        List<Value> values = valueRepository.findAll();
        log.info("getAllTemplates value : {} ", values);

        return values.stream()
                .map(ValueMapper::mapToValueDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = {"valueCache"}, key = "#valueId")
    public Optional<ValueDto> getByIdValue(Long valueId) {
        Optional<Value> value = valueRepository.findById(valueId);
        ValueDto valueDto = ValueMapper.mapToValueDto(value.orElseThrow(
                () -> new ValueNotFoundException("Такого шаблона нет id : " + valueId)
        ));
        log.info("getByIdTemplate valueDto : {} ", valueDto);
        return Optional.of(valueDto);
    }

    @Override
    @Transactional
//    @CachePut(cacheNames = {"valueCache"}, key = "#valueDto")
    public Value createValue(ValueDto valueDto) {
        Value value = ValueMapper.mapToValue(valueDto);
        log.info("createTemplate value : {} ", value);
        return valueRepository.save(value);
    }

    @Override
    @Transactional
//    @CachePut(cacheNames = {"valueCache"}, key = "#valueDto")
    public Value updateValue(Long valueId, ValueDto valueDto) {
        Optional<Value> value = valueRepository.findById(valueId);
        ValueDto check = ValueMapper.mapToValueDto(value.orElseThrow(
                () -> new ValueNotFoundException("Такого Value нет id : " + valueId)
        ));

        if (check.equals(valueDto)) {
            throw new ValueNotFoundException("Такой Value уже существует : " + valueDto);
        }

        List<String> findCreateValues = value.get().getCreateValues().stream()
                .map(project.entity.CreateValue::getJsonValue)
                .collect(Collectors.toList());
        log.debug("Получил все CreateValue::getJsonValue в виде строки : {} ", findCreateValues);

        if (valueDto.getCreateValue() != null
                && !findCreateValues.contains(ObjectMapperUtil.setValue(valueDto.getCreateValue()))) {
            CreateValueDto createValueDto = new CreateValueDto();
            createValueDto.setValue(value.get());
            createValueDto.setJsonValue(valueDto.getCreateValue());
            createValueRepository.save(CreateValueMapper.mapToCreateValue(createValueDto));
        }

        List<String> findUpdateValue = value.get().getUpdateValues().stream()
                .map(project.entity.UpdateValue::getJsonValue)
                .collect(Collectors.toList());
        log.debug("Получил все UpdateValue::getJsonValue в виде строки : {} ", findUpdateValue);

        if (valueDto.getUpdateValue() != null
                && !findUpdateValue.contains(ObjectMapperUtil.setValue(valueDto.getUpdateValue()))) {
            UpdateValueDto updateValueDto = new UpdateValueDto();
            updateValueDto.setValue(value.get());
            updateValueDto.setJsonValue(valueDto.getUpdateValue());
            updateValueRepository.save(UpdateValueMapper.mapToUpdateValue(updateValueDto));
        }

        value.get().setCreateValue(valueDto.getUpdateValue());
        value.get().setUpdateValue(valueDto.getCreateValue());

        log.info("updateValue value : {} ", value.get());
        return value.get();
    }

    @Override
    @Transactional
//    @CacheEvict(cacheNames = {"valueCache"}, key = "#valueId")
    public void deleteByIdValue(Long valueId) {
        Optional<project.entity.Value> value = valueRepository.findById(valueId);
        value.orElseThrow(
                () -> new ValueNotFoundException("Такого Value нет id : " + valueId)
        ).setIsArchive(true);
        log.info("deleteByIdTemplate value.setIsArchive(true) : {} ", value.get());
        valueRepository.save(value.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValueDto> getAllByServerId(Long serverId) {
        List<Value> value = valueRepository.findAllByServiceId(serverId);
        log.info("getAllByServerId value : {} ", value);

        return value.stream()
                .map(ValueMapper::mapToValueDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object> getAllUpdateValueDtoByValue(Long valueId) {
        Optional<Value> value = valueRepository.findById(valueId);
        log.info("getAllUpdateValueDtoByValue value : {} ", value);

        return value.orElseThrow(
                        () -> new ValueNotFoundException("Такого Value нет id : " + valueId)
                ).getUpdateValues().stream()
                .map(UpdateValue::getJsonValue)
                .map(ParseJson::parse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object> getAllCreateValueDtoByValue(Long valueId) {
        Optional<Value> value = valueRepository.findById(valueId);
        log.info("getAllCreateValueDtoByValue value : {} ", value);

        return value.orElseThrow(
                        () -> new ValueNotFoundException("Такого Value нет id : " + valueId)
                ).getCreateValues().stream()
                .map(CreateValue::getJsonValue)
                .map(ParseJson::parse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object> getAllCreateValueByServiceId(Long serviceId) {
        List<Value> values = valueRepository.findAllByServiceId(serviceId);

        List<Object> createValue = values.stream()
                .map(Value::getCreateValue)
                .collect(Collectors.toList());
        log.info("getAllCreateValueByServiceId createValue : {} ", createValue);

        return createValue;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object> getAllUpdateValueByServiceId(Long serviceId) {
        List<Value> values = valueRepository.findAllByServiceId(serviceId);

        List<Object> updateValue = values.stream()
                .map(Value::getUpdateValue)
                .collect(Collectors.toList());
        log.info("getAllCreateValueByServiceId updateValue : {} ", updateValue);

        return updateValue;
    }
}
