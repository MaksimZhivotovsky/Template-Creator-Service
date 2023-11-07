package project.mapper;

import project.dto.UpdateValueDto;
import project.entity.UpdateValue;

import java.time.LocalDateTime;

public class UpdateValueMapper {

    private UpdateValueMapper() {
    }

    // Convert UpdateValue JPA Entity into UpdateValueDto
    public static UpdateValueDto mapToUpdateValueDto(UpdateValue updateValue) {
        return new UpdateValueDto(
                updateValue.getJsonValue(),
                updateValue.getValue()
        );
    }

    // Convert UpdateValueDto into User JPA UpdateValue
    public static UpdateValue mapToUpdateValue(UpdateValueDto updateValueDto) {
        UpdateValue updateValue = new UpdateValue();
        updateValue.setJsonValue(updateValueDto.getJsonValue());
        updateValue.setValue(updateValueDto.getValue());
        updateValue.setTimestamp(LocalDateTime.now());
        updateValue.setIsArchive(false);
        return updateValue;
    }
}
