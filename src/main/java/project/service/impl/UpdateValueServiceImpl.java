package project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.UpdateValueDto;
import project.entity.UpdateValue;
import project.entity.Value;
import project.exceptions.UpdateValueNotFoundException;
import project.exceptions.ValueNotFoundException;
import project.mapper.UpdateValueMapper;
import project.repository.UpdateValueRepository;
import project.repository.ValueRepository;
import project.service.UpdateValueService;
import project.utils.ParseJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateValueServiceImpl implements UpdateValueService {

    private final UpdateValueRepository updateValueRepository;
    private final ValueRepository valueRepository;

    @Override
    @Transactional
    @CachePut(cacheNames = {"updateCache"}, key = "#updateValueDto")
    public UpdateValue createUpdateValue(Long valueId, UpdateValueDto updateValueDto) {

        Optional<Value> v = valueRepository.findById(valueId);
        updateValueDto.setValue(v.orElseThrow(
                () ->  new ValueNotFoundException("Такого шаблона нет id : " + valueId)
        ));
        UpdateValue updateValue = UpdateValueMapper.mapToUpdateValue(updateValueDto);
        log.info("createUpdateValue updateValue : {} ", updateValue);
        return updateValueRepository.save(updateValue);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = {"updateCache"}, key = "#updateValueId")
    public Optional<UpdateValueDto> getByIdUpdateValue(Long updateValueId) {
        Optional<UpdateValue> updateValue = updateValueRepository.findById(updateValueId);
        UpdateValueDto updateValueDto = UpdateValueMapper
                .mapToUpdateValueDto(updateValue.orElseThrow(
                        () -> new UpdateValueNotFoundException("Такого до создание запроса для шаблона нет id : " + updateValueId)
                ));
        log.info("getByIdUpdateValue updateValueDto : {} ", updateValueDto);
        return Optional.of(updateValueDto);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"updateCache"}, key = "#updateValueId")
    public void deleteByIdUpdateValue(Long updateValueId) {
        Optional<UpdateValue> updateValue = updateValueRepository.findById(updateValueId);
        if (updateValue.isEmpty()) {
            throw new UpdateValueNotFoundException("Такого до создание запроса для шаблона нет id : " + updateValueId);
        }
        log.info("deleteByIdUpdateValue updateValue.setIsArchive(true): {} ", updateValue);
        updateValue.get().setIsArchive(true);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = {"updateCache"}, key = "#valueId")
    public List<Object> findAllByValueId(Long valueId) {
        List<Object> updateValues = new ArrayList<>();
        for (UpdateValue updateValue : updateValueRepository.findAllByValueValueId(valueId)) {
            updateValues.add(ParseJson.parse(updateValue.getJsonValue()));
        }

        log.info("findAllByValueId updateValues: {} ", updateValues);
        return updateValues;
    }
}
