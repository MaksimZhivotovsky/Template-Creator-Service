package project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import project.utils.ObjectMapperUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "templateId")
@ToString(of = {"templateId", "createValue", "updateValue"})
@Table(name = "templates")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Template шаблона")

//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Template implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    @Schema(description = "Идентификатор value")
    private Long templateId;

    @Column(name = "create_value")
    @Schema(description = "Строка создания шаблона")
    private String createValue;

    @Column(name = "update_value")
    @Schema(description = "Строка для до создания шаблона")
    private String updateValue;

    @Column(name = "service_id")
    @Schema(description = "ID сервиса к которому относится Value")
    private Long serviceId;

    @Column(name = "organization_id")
    @Schema(description = "ID сервиса к которому относится Value")
    private Long organizationId;

    @Column(name = "create_data")
    @Schema(description = "Время создания")
    private LocalDateTime createData = LocalDateTime.now();

    @Column(name = "modify_data")
    @Schema(description = "Время создания")
    private LocalDateTime modifyData = LocalDateTime.now();

    @Column(name = "is_archive")
    @Schema(description = "Флаг находится ли value в архиве")
    private Boolean isArchive;

    public void setCreateData(final LocalDateTime createData) {
        this.createData = createData;
    }

//    public void setUpdateValue(Object updateValue) {
//        this.updateValue = ObjectMapperUtil.setValue(updateValue);
//    }
//
//    public void setCreateValue(Object createValue) {
//        this.createValue = ObjectMapperUtil.setValue(createValue);
//    }

}
