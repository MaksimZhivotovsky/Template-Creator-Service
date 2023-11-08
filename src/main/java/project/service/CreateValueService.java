package project.service;

import project.dto.CreateValueDto;
import project.entity.CreateValue;

import java.util.List;
import java.util.Optional;

public interface CreateValueService {

    CreateValue createCreateValue(Long valueId, CreateValueDto createValueDto);

    void deleteByIdCreateValue(Long createValueId);

    List<Object> getAllByValueId(Long valueId);

    Optional<Object> getByIdCreateValue(Long createValueId);

}
