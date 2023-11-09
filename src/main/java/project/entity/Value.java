package project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import project.dto.CreateValueDto;
import project.dto.UpdateValueDto;
import project.mapper.CreateValueMapper;
import project.mapper.UpdateValueMapper;
import project.utils.ObjectMapperUtil;
import project.utils.ParseJson;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "valueId")
@ToString(of = {"valueId", "createValue", "updateValue"})
@Table(name = "values")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Value для создания шаблона")

//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Value implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "value_id")
    @Schema(description = "Идентификатор value")
    private Long valueId;

    @Column(name = "create_value")
    @Schema(description = "Строка создания шаблона")
    private String createValue;

    @Column(name = "update_value")
    @Schema(description = "Строка для до создания шаблона")
    private String updateValue;

    @Column(name = "service_id")
    @Schema(description = "ID сервиса к которому относится Value")
    private Long serviceId;

    @Column(name = "timestamp")
    @Schema(description = "Время создания")
    private LocalDateTime timestamp;

    @Column(name = "is_archive")
    @Schema(description = "Флаг находится ли value в архиве")
    private Boolean isArchive;

    @OneToMany(mappedBy = "value", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    @Schema(description = "Хранит историю изменений JSON строк шаблона")
    private List<CreateValue> createValues = new ArrayList<>();

    @OneToMany(mappedBy = "value", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    @Schema(description = "Хранит историю изменений JSON строк для до создания шаблона")
    private List<UpdateValue> updateValues = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "values")
    @JsonBackReference
//    @JsonManagedReference
    private List<Template> templates = new ArrayList<>();

    public void setCreateValues(CreateValue createValue) {
        createValues.add(createValue);
    }

    public void setUpdateValues(UpdateValue updateValue) {
        updateValues.add(updateValue);
    }

    public void setUpdateValue(Object updateValue) {
        this.updateValue = ObjectMapperUtil.setValue(updateValue);
        UpdateValueDto updateValueDto = new UpdateValueDto();
        updateValueDto.setJsonValue(updateValue);
        updateValueDto.setValue(this);
        UpdateValue updateValueData = UpdateValueMapper.mapToUpdateValue(updateValueDto);
        setUpdateValues(updateValueData);
    }

    public void setCreateValue(Object createValue) {
        this.createValue = ObjectMapperUtil.setValue(createValue);
        CreateValueDto createValueDto = new CreateValueDto();
        createValueDto.setJsonValue(createValue);
        createValueDto.setValue(this);
        CreateValue createValueData = CreateValueMapper.mapToCreateValue(createValueDto);
        setCreateValues(createValueData);
    }

    public Object getUpdateValue() {
        return ParseJson.parse(this.updateValue);
    }

    public Object getCreateValue() {
        return ParseJson.parse(this.createValue);
    }
}
