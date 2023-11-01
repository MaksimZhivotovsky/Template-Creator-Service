package project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import project.utils.ObjectMapperUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(of = {"jsonValue"})
@EqualsAndHashCode(of = "jsonTemplateId")
@NoArgsConstructor
@Table(name = "json_templates")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "json_template_id")
    private Long jsonTemplateId;

    @Column(name = "json_value")
    private String jsonValue;

    @Column(name = "is_archive")
    private Boolean isArchive;

    @Column(name = "timestamp")
    private Date timestamp = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    @JsonBackReference
    private Template template;

    public void setJsonValue(Object jsonTemplate) {
        this.jsonValue = ObjectMapperUtil.setValue(jsonTemplate);
    }

}
