package project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "templateId")
@ToString(of = {"templateId","name"})
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "name")
    private String name;

    @Column(name = "json_value")
    private String jsonValue;

    @Column(name = "is_archive")
    private Boolean isArchive = false;

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<HistoryTemplate> historyTemplates;

    public void setJsonValue(Object jsonValue) throws JsonProcessingException {
        ObjectMapper Obj = new ObjectMapper();
        this.jsonValue = Obj.writeValueAsString(jsonValue);
    }

    public HistoryTemplate setHistoryTemplates(HistoryTemplate historyTemplate) {
        historyTemplates.add(historyTemplate);
        return historyTemplate;
    }
}
