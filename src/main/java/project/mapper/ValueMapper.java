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
        valueDto.setJsonValue(value.getJsonValue());
        valueDto.setUpdateValue(value.getUpdateValue());
        valueDto.setOrganizationId(value.getOrganizationId());

        return valueDto;
    }

    // Convert ValueDto into User JPA Value
    public static Value mapToValue(ValueDto valueDto) {
        Value value = new Value();
        value.setServiceId(valueDto.getServiceId());
        value.setUpdateValue(valueDto.getUpdateValue());
        value.setJsonValue(valueDto.getJsonValue());
        value.setIsArchive(false);
        value.setCreateData(LocalDateTime.now());
        value.setOrganizationId(valueDto.getOrganizationId());
        return value;
    }
}
