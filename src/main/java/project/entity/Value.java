package project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import project.utils.ObjectMapperUtil;


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
@ToString(of = {"valueId", "jsonValue", "updateValue"})
@Table(name = "values")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Value для создания шаблона")

//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Value implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Идентификатор value")
    private Long id;

    @Column(name = "json_value")
    @Schema(description = "Строка создания шаблона")
    private String jsonValue;

    @Column(name = "update_value")
    @Schema(description = "Строка для до создания шаблона")
    private String updateValue;

    @Column(name = "service_id")
    @Schema(description = "ID сервиса к которому относится Value")
    private Long serviceId;

    @Column(name = "organization_id")
    @Schema(description = "ID организации к которому относится Value")
    private Long organizationId;

    @Column(name = "create_data")
    @Schema(description = "Время создания")
    private LocalDateTime createData;

    @Column(name = "modify_data")
    @Schema(description = "Время обновления")
    private LocalDateTime modifyData;

    @Column(name = "is_archive")
    @Schema(description = "Флаг находится ли value в архиве")
    private Boolean isArchive;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "values")
    @JsonBackReference
//    @JsonManagedReference
    private List<Template> templates = new ArrayList<>();


//    public void setUpdateValue(Object updateValue) {
//        this.updateValue = ObjectMapperUtil.setValue(updateValue);
//    }
//
//    public void setJsonValue(Object jsonValue) {
//        this.jsonValue = ObjectMapperUtil.setValue(jsonValue);
//    }

}
