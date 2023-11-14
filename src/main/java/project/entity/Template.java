package project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "templateId")
@ToString(of = {"templateId", "name", "jsonValue", "updateJsonValue"})
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

    @Column(name = "name")
    @Schema(description = "Имя шаблона")
    private String name;

    @Column(name = "json_value")
    @Schema(description = "Строка создания шаблона")
    private String jsonValue;

    @Column(name = "update_json_value")
    @Schema(description = "Строка для до создания шаблона")
    private String updateJsonValue;

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


}
