package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDto {

    private String templateName;
    private Long serviceId;
    private List<ValueDto> valueDtos = new ArrayList<>();

}
