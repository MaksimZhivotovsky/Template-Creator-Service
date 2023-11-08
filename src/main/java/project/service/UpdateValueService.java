package project.service;

import project.dto.UpdateValueDto;
import project.entity.UpdateValue;

import java.util.List;
import java.util.Optional;

public interface UpdateValueService {
    UpdateValue createUpdateValue(Long valueId, UpdateValueDto updateValueDto);

    Optional<Object> getByIdUpdateValue(Long updateValueId);

    void deleteByIdUpdateValue(Long updateValueId);

    List<Object> findAllByValueId(Long valueId);
}
