package project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import project.utils.ObjectMapperUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(of = {"jsonValue"})
@EqualsAndHashCode(of = {"updateValueId"})
@NoArgsConstructor
@Table(name = "update_values")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE)
@Schema(description = "Запрос для до создание шаблона")
public class UpdateValue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "update_value_id")
    @Schema(description = "Идентификатор запроса для до создания шаблона")
    private Long updateValueId;

    @Column(name = "json_value")
    @Schema(description = "JSON строка для до создание шаблона")
    private String jsonValue;

    @Column(name = "is_archive")
    @Schema(description = "Флаг находится ли строка для до создание шаблона в архиве")
    private Boolean isArchive;

    @Column(name = "timestamp")
    @Schema(description = "Время создания")
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "value_id")
    @JsonBackReference
    @Schema(description = "Шаблон")
    private Value value;

    public void setJsonValue(Object jsonTemplate) {
        this.jsonValue = ObjectMapperUtil.setValue(jsonTemplate);
    }

}
