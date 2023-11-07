package project.mapper;

import project.dto.ValueDto;
import project.entity.Value;

import java.time.LocalDateTime;

public class ValueMapper {

    private ValueMapper() {
    }

    // Convert Value JPA Entity into ValueDto
    public static ValueDto mapToValueDto(Value value) {
        ValueDto valueDto = new ValueDto();
        valueDto.setServiceId(value.getServiceId());
        valueDto.setCreateValue(value.getUpdateValue());
        valueDto.setUpdateValue(value.getCreateValue());

        return valueDto;
    }

    // Convert ValueDto into User JPA Value
    public static Value mapToValue(ValueDto valueDto) {
        Value value = new Value();
        value.setServiceId(valueDto.getServiceId());
        value.setUpdateValue(valueDto.getCreateValue());
        value.setCreateValue(valueDto.getUpdateValue());
        value.setIsArchive(false);
        value.setTimestamp(LocalDateTime.now());
        return value;
    }
}
