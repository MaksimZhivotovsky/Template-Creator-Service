package project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import project.utils.ObjectMapperUtil;
import project.utils.ParseJson;

import javax.persistence.*;
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

@Cache(region = "valueCache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Schema(description = "Value для создания шаблона")
public class Value {

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

    public void setCreateValues(CreateValue createValue) {
        createValues.add(createValue);
    }

    public void setUpdateValues(UpdateValue updateValue) {
        updateValues.add(updateValue);
    }

    public void setUpdateValue(Object updateValue) {
        this.updateValue = ObjectMapperUtil.setValue(updateValue);
    }

    public void setCreateValue(Object jsonTemplate) {
        this.createValue = ObjectMapperUtil.setValue(jsonTemplate);
    }

    public Object getUpdateValue() {
        return ParseJson.parse(this.updateValue);
    }

    public Object getCreateValue() {
        return ParseJson.parse(this.createValue);
    }
}
