package project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.ValueDto;
import project.entity.Value;
import project.exceptions.ValueNotFoundException;
import project.mapper.ValueMapper;
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

    @Override
    @Transactional(readOnly = true)
    public List<Value> getAllValuesByOrganizationId(Long organizationId) {
        List<Value> values = valueRepository.findAllByOrganizationId(organizationId);
        log.info("getAllTemplates value : {} ", values);

        return values;
    }

    @Override
    @Transactional
//    @CachePut(cacheNames = {"valueCache"}, key = "#valueDto")
    public Value createValue(String keycloakId, ValueDto valueDto) {
        CheckUser.check(keycloakId, valueDto.getOrganizationId());
        Value value = ValueMapper.mapToValue(valueDto);

        List<Value> valueList = valueRepository.findAllByOrganizationId(value.getOrganizationId());

        List<String> jsonValueList = valueList.stream()
                .map(Value::getJsonValue)
                .collect(Collectors.toList());
        if(!jsonValueList.contains(valueDto.getJsonValue())) {
            value.setJsonValue(valueDto.getJsonValue());
        } else {
            throw new ValueNotFoundException("Такой JsonValue уже существует : " + valueDto.getJsonValue());
        }

        List<String> updateValueList = valueList.stream()
                .map(Value::getUpdateValue)
                .collect(Collectors.toList());
        if(!updateValueList.contains(valueDto.getUpdateValue())) {
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
    public Value updateValue(String keycloakId, Long valueId, ValueDto valueDto) {
        CheckUser.check(keycloakId, valueDto.getOrganizationId());
        List<Value> valueList = valueRepository.findAllByOrganizationId(valueDto.getOrganizationId());

        Optional<Value> value = valueList.stream()
                .filter(v -> v.getId().equals(valueId))
                .findFirst();

        List<String> jsonValueList = valueList.stream()
                        .map(Value::getJsonValue)
                        .collect(Collectors.toList());
        if(!jsonValueList.contains(valueDto.getJsonValue())) {
            value.orElseThrow(
                    () -> new ValueNotFoundException("У этой организации нет такого Value id : " + valueId)
            ).setJsonValue(valueDto.getJsonValue());
        }

        List<String> updateValueList = valueList.stream()
                .map(Value::getUpdateValue)
                .collect(Collectors.toList());
        if(!updateValueList.contains(valueDto.getUpdateValue())) {
            value.orElseThrow().setUpdateValue(valueDto.getUpdateValue());
        }

        if(valueDto.getServiceId() != null) {
            value.orElseThrow().setServiceId(valueDto.getServiceId());
        }

        value.orElseThrow(
                () -> new ValueNotFoundException("У этой организации нет такого Value id : " + valueId)
        ).setOrganizationId(value.get().getOrganizationId());
        value.get().setModifyData(LocalDateTime.now());
        log.info("updateValue value : {} ", value.get());
        return value.get();
    }

    @Override
    @Transactional
//    @CacheEvict(cacheNames = {"valueCache"}, key = "#valueId")
    public void deleteByIdValue(String keycloakId, Long valueId) {
        Optional<Value> value = valueRepository.findById(valueId);

        CheckUser.check(keycloakId, value.orElseThrow(
                () -> new ValueNotFoundException("У этой организации нет такого Value id : " + valueId)
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
    public List<String> getAllJsonValueByServiceId(Long serviceId) {
        List<Value> values = valueRepository.findAllByServiceId(serviceId);

        List<String> jsonValue = values.stream()
                .map(Value::getJsonValue)
                .collect(Collectors.toList());
        log.info("getAllJsonValueByServiceId jsonValue : {} ", jsonValue);

        return jsonValue;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllUpdateValueByServiceId(Long serviceId) {
        List<Value> values = valueRepository.findAllByServiceId(serviceId);

        List<String> updateValue = values.stream()
                .map(Value::getUpdateValue)
                .collect(Collectors.toList());
        log.info("getAllCreateValueByServiceId updateValue : {} ", updateValue);

        return updateValue;
    }
}
