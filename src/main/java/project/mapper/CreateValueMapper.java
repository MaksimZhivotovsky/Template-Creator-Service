package project.mapper;

import project.dto.CreateValueDto;
import project.entity.CreateValue;

import java.time.LocalDateTime;

public class CreateValueMapper {

    private CreateValueMapper() {
    }

    // Convert CreateValue JPA Entity into CreateValueDto
    public static CreateValueDto mapToCreateValueDto(CreateValue createValue) {
        return new CreateValueDto(
                createValue.getJsonValue(),
                createValue.getValue()
        );
    }

    // Convert CreateValueDto into User JPA CreateValue
    public static CreateValue mapToCreateValue(CreateValueDto createValueDto) {
        CreateValue createValue = new project.entity.CreateValue();
        createValue.setJsonValue(createValueDto.getJsonValue());
        createValue.setValue(createValueDto.getValue());
        createValue.setTimestamp(LocalDateTime.now());
        createValue.setIsArchive(false);
        return createValue;
    }
}
