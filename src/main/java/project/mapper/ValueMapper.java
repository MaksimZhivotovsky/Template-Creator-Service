package project.mapper;

import project.dto.ValueDto;

import java.time.LocalDateTime;

public class ValueMapper {

    private ValueMapper() {
    }

    // Convert Value JPA Entity into ValueDto
    public static ValueDto mapToValueDto(project.entity.Value value) {
        ValueDto valueDto = new ValueDto();
        valueDto.setServiceId(value.getServiceId());
        valueDto.setCreateValue(value.getCreateValue());
        valueDto.setUpdateValue(value.getUpdateValue());

        return valueDto;
    }

    // Convert ValueDto into User JPA Value
    public static project.entity.Value mapToValue(ValueDto valueDto) {
        project.entity.Value value = new project.entity.Value();
        value.setServiceId(valueDto.getServiceId());
        value.setUpdateValue(valueDto.getUpdateValue());
        value.setCreateValue(valueDto.getCreateValue());
        value.setIsArchive(false);
        value.setTimestamp(LocalDateTime.now());
        return value;
    }
}
