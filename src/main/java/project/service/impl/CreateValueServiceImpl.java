package project.service.impl;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateValueServiceImpl implements CreateValueService {

    private final CreateValueRepository createValueRepository;
    private final ValueRepository valueRepository;

    @Override
    @Transactional
    @CachePut(cacheNames = {"createValueCache"}, key = "#createValueDto")
    public CreateValue createCreateValue(Long valueId, CreateValueDto createValueDto) {
        Optional<Value> value = valueRepository.findById(valueId);
        createValueDto.setValue(value.orElseThrow(
                () ->  new ValueNotFoundException("Такого value нет id : " + valueId)
        ));
        CreateValue createValue = CreateValueMapper.mapToCreateTemplate(createValueDto);

        log.info("createCreateValue createValue : {} ", createValue);
        return createValueRepository.save(createValue);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"createValueCache"}, key = "#createValueId")
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
    @Cacheable(cacheNames = {"createValueCache"}, key = "#createValueId")
    public Optional<CreateValueDto> getByIdCreateValue(Long createValueId) {
        Optional<CreateValue> createValue = createValueRepository.findById(createValueId);
        CreateValueDto createValueDto = CreateValueMapper.mapToCreateValueDto(createValue.orElseThrow(
                () -> new CreateValueNotFoundException("Такого запроса для шаблона нет id : " + createValueId)
        ));

        log.info("getByIdJsonTemplate createValueDto : {} ", createValueDto);
        return Optional.of(createValueDto);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = {"createValueCache"}, key = "#valueId")
    public List<Object> getAllByValueId(Long valueId) {
        List<Object> createValues = new ArrayList<>();
        for (CreateValue createValue : createValueRepository.findAllByValueValueId(valueId)) {
            createValues.add(ParseJson.parse(createValue.getJsonValue()));
        }
        log.info("getAllByTemplateId createValues : {} ", createValues);
        return createValues;
    }
}
