package project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import project.utils.ObjectMapperUtil;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(of = {"jsonValue"})
@EqualsAndHashCode(of = "postCreateTemplateId")
@NoArgsConstructor
@Table(name = "post_create_templates")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Запрос для до создание шаблона")
public class PostCreateTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_create_template_id")
    @Schema(description = "Идентификатор запроса для до создания шаблона")
    private Long postCreateTemplateId;

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
    @JoinColumn(name = "template_id")
    @JsonBackReference
    @Schema(description = "Шаблон")
    private Template template;

    public void setJsonValue(Object jsonTemplate) {
        this.jsonValue = ObjectMapperUtil.setValue(jsonTemplate);
    }

}
