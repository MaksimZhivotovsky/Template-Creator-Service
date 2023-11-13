package project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.UserRcDto;
import project.dto.ValueDto;
import project.entity.Value;
import project.exceptions.ValueNotFoundException;
import project.mapper.ValueMapper;
import project.repository.UserRcSQLRepository;
import project.repository.ValueRepository;
import project.service.ValueService;
import project.utils.CheckUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValueServiceImpl implements ValueService {

    private final ValueRepository valueRepository;
    private final UserRcSQLRepository userRcSQLRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ValueDto> getAllValues(Long keycloakId) {
        List<Value> values = valueRepository.findAllByOrganizationId(keycloakId);
        log.info("getAllTemplates value : {} ", values);

        return values.stream()
                .map(ValueMapper::mapToValueDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = {"valueCache"}, key = "#valueId")
    public Optional<ValueDto> getByIdValue(Long keycloakId, Long valueId) {
        Optional<Value> value = valueRepository.findById(valueId);
        CheckUser.check(keycloakId, value.orElseThrow(
                () -> new ValueNotFoundException("Такого шаблона нет id : " + valueId)
        ).getOrganizationId());
        ValueDto valueDto = ValueMapper.mapToValueDto(value.orElseThrow());
        log.info("getByIdTemplate valueDto : {} ", valueDto);
        return Optional.of(valueDto);
    }

    @Override
    @Transactional
//    @CachePut(cacheNames = {"valueCache"}, key = "#valueDto")
    public Value createValue(Long keycloakId, ValueDto valueDto) {
        Value value = ValueMapper.mapToValue(valueDto);

        UserRcDto userRcDto = userRcSQLRepository.findUserByKcId(keycloakId);
        value.setOrganizationId(userRcDto.getOrganizationId());

        List<Value> valueList = valueRepository.findAll();

        List<String> jsonValueList = valueList.stream()
                .map(Value::getJsonValue)
                .collect(Collectors.toList());
        if(!jsonValueList.contains(valueDto.getJsonValue().toString())) {
            value.setJsonValue(valueDto.getJsonValue());
        } else {
            throw new ValueNotFoundException("Такой JsonValue уже существует : " + valueDto.getJsonValue());
        }

        List<String> updateValueList = valueList.stream()
                .map(Value::getUpdateValue)
                .collect(Collectors.toList());
        if(!updateValueList.contains(valueDto.getUpdateValue().toString())) {
            value.setUpdateValue(valueDto.getUpdateValue());
        } else {
            throw new ValueNotFoundException("Такой UpdateValue уже существует : " + valueDto.getUpdateValue());
        }

        value.setModifyData(LocalDateTime.now());

        log.info("createTemplate value : {} ", value);
        return valueRepository.save(value);
    }

    @Override
    @Transactional
//    @CachePut(cacheNames = {"valueCache"}, key = "#valueDto")
    public Value updateValue(Long keycloakId, Long valueId, ValueDto valueDto) {
        List<Value> valueList = valueRepository.findAll();

        Optional<Value> value = valueList.stream()
                .filter(v -> v.getId().equals(valueId))
                .findFirst();

        CheckUser.check(keycloakId, value.orElseThrow(
                () -> new ValueNotFoundException("Такого Value нет id : " + valueId)
        ).getOrganizationId());

        List<String> jsonValueList = valueList.stream()
                        .map(Value::getJsonValue)
                        .collect(Collectors.toList());
        if(!jsonValueList.contains(valueDto.getJsonValue().toString())) {
            value.get().setJsonValue(valueDto.getJsonValue());
        }

        List<String> updateValueList = valueList.stream()
                .map(Value::getUpdateValue)
                .collect(Collectors.toList());
        if(!updateValueList.contains(valueDto.getUpdateValue().toString())) {
            value.get().setUpdateValue(valueDto.getUpdateValue());
        }

        if(valueDto.getServiceId() != null) {
            value.get().setServiceId(valueDto.getServiceId());
        }

        value.get().setOrganizationId(value.get().getOrganizationId());
        value.get().setModifyData(LocalDateTime.now());
        log.info("updateValue value : {} ", value.get());
        return value.get();
    }

    @Override
    @Transactional
//    @CacheEvict(cacheNames = {"valueCache"}, key = "#valueId")
    public void deleteByIdValue(Long keycloakId, Long valueId) {
        Optional<Value> value = valueRepository.findById(valueId);

        CheckUser.check(keycloakId, value.orElseThrow(
                () -> new ValueNotFoundException("Такого Value нет id : " + valueId)
        ).getOrganizationId());

        value.orElseThrow().setIsArchive(true);
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
    public List<String> getAllJsonValueByOrganization(Long organizationId) {
        List<Value> valueList = valueRepository.findAllByOrganizationId(organizationId);

        List<String> jsonValue = valueList.stream()
                .map(Value::getJsonValue)
                .collect(Collectors.toList());
        log.info("getAllJsonValueByOrganization jsonValue : {} ", jsonValue);
        return jsonValue;
    }

    @Override
    public List<String> getAllUpdateValueByOrganizationId(Long organizationId) {
        List<Value> valueList = valueRepository.findAllByOrganizationId(organizationId);

        List<String> updateValue = valueList.stream()
                .map(Value::getUpdateValue)
                .collect(Collectors.toList());
        log.info("getAllUpdateValueByOrganizationId updateValue : {}", updateValue);
        return updateValue;
    }

    @Transactional(readOnly = true)
    public List<Object> getAllJsonValueByServiceId(Long serviceId) {
        List<Value> values = valueRepository.findAllByServiceId(serviceId);

        List<Object> jsonValue = values.stream()
                .map(Value::getJsonValue)
                .collect(Collectors.toList());
        log.info("getAllJsonValueByServiceId jsonValue : {} ", jsonValue);

        return jsonValue;
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
