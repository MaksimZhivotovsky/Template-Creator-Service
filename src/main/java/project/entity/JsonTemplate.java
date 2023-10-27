package project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(of = {"jsonValue"})
@EqualsAndHashCode(of = "jsonTemplateId")
@NoArgsConstructor
@Table(name = "json_templates")
public class JsonTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "json_template_id")
    private Long jsonTemplateId;

    @Column(name = "json_value")
    private String jsonValue;

    @Column(name = "is_archive")
    private Boolean isArchive = false;

    @Column(name = "timestamp")
    private Date timestamp = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    @JsonBackReference
    private Template template;

    public void setJsonValue(Object jsonValue) {
        ObjectMapper Obj = new ObjectMapper();
        try {
            this.jsonValue = Obj.writeValueAsString(jsonValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
