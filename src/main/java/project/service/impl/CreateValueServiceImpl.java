package project.service.impl;

import liquibase.pro.packaged.S;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.CreateValueDto;
import project.entity.CreateValue;
import project.entity.Value;
import project.exceptions.CreateValueNotFoundException;
import project.exceptions.ValueNotFoundException;
import project.mapper.CreateValueMapper;
import project.repository.CreateValueRepository;
import project.repository.ValueRepository;
import project.service.CreateValueService;
import project.utils.ParseJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateValueServiceImpl implements CreateValueService {

    private final CreateValueRepository createValueRepository;
    private final ValueRepository valueRepository;

    @Override
    @Transactional
//    @CachePut(cacheNames = {"createValueCache"}, key = "#createValueDto")
    public CreateValue createCreateValue(Long valueId, CreateValueDto createValueDto) {
        Optional<Value> value = valueRepository.findById(valueId);
        createValueDto.setValue(value.orElseThrow(
                () ->  new ValueNotFoundException("Такого value нет id : " + valueId)
        ));
        CreateValue createValue = CreateValueMapper.mapToCreateValue(createValueDto);

        List<String> createValueList = value.get().getCreateValues().stream()
                        .map(CreateValue::getJsonValue)
                        .collect(Collectors.toList());

        if (createValueList.contains(createValue.getJsonValue())) {
            throw new CreateValueNotFoundException("Такой JsonValue существует : " + createValue.getJsonValue());
        }

        log.info("createCreateValue createValue : {} ", createValue);
        return createValueRepository.save(createValue);
    }

    @Override
    @Transactional
//    @CacheEvict(cacheNames = {"createValueCache"}, key = "#createValueId")
    public void deleteByIdCreateValue(Long createValueId) {
        Optional<CreateValue> createValue = createValueRepository.findById(createValueId);
        if (createValue.isEmpty()) {
            throw new CreateValueNotFoundException("Такого запроса для шаблона нет id : " + createValueId);
        }
        log.info("deleteByIdJsonTemplate createValue.setIsArchive(true) : {} ", createValue);
        createValue.get().setIsArchive(true);
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = {"createValueCache"}, key = "#createValueId")
    public Optional<Object> getByIdCreateValue(Long createValueId) {
        Optional<CreateValue> createValue = createValueRepository.findById(createValueId);
        Object createValueJSON = ParseJson.parse(createValue.orElseThrow(
                () -> new CreateValueNotFoundException("Такого запроса для шаблона нет id : " + createValueId)
        ).getJsonValue());
        log.info("getByIdJsonTemplate createValueDto : {} ", createValue.get().getCreateValueId());
        return Optional.of(createValueJSON);
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = {"createValueCache"}, key = "#valueId")
    public List<Object> getAllByValueId(Long valueId) {
        List<Object> createValues = new ArrayList<>();
        for (CreateValue createValue : createValueRepository.findAllByValueValueId(valueId)) {
            createValues.add(ParseJson.parse(createValue.getJsonValue()));
        }
        log.info("getAllByTemplateId createValues : {} ", createValues);
        return createValues;
    }
}
