package project.service;

import project.dto.ValueDto;
import project.entity.Value;

import java.util.List;
import java.util.Optional;

public interface ValueService {

    List<ValueDto> getAllValues();

    Optional<ValueDto> getByIdValue(Long valueId);

    Value createValue(ValueDto valueDto);

    Value updateValue(Long valueId, ValueDto valueDto);

    void deleteByIdValue(Long valueId);

    List<ValueDto> getAllByServerId(Long serverId);


}
