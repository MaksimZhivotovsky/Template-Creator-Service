package project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import project.utils.ObjectMapperUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(of = {"jsonValue"})
@EqualsAndHashCode(of = "postCreateTemplateId")
@NoArgsConstructor
@Table(name = "post_create_template")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCreateTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_create_template_id")
    private Long postCreateTemplateId;

    @Column(name = "json_value")
    private String jsonValue;

    @Column(name = "is_archive")
    private Boolean isArchive;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    @JsonBackReference
    private Template template;

    public void setJsonValue(Object jsonTemplate) {
        this.jsonValue = ObjectMapperUtil.setValue(jsonTemplate);
    }

}
