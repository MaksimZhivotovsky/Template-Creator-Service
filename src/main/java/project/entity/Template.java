package project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import project.utils.ObjectMapperUtil;
import project.utils.ParseJson;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "templateId")
@ToString(of = {"templateId","name"})
@Table(name = "templates")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Template implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "name")
    private String name;

    @Column(name = "json_template")
    private String jsonTemplate;

    @Column(name = "post_create_template")
    private String postCreateTemplate;

    @Column(name = "is_archive")
    private Boolean isArchive;

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<JsonTemplate> jsonTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PostCreateTemplate> postCreateTemplates = new ArrayList<>();

    public JsonTemplate setJsonTemplates(JsonTemplate jsonTemplate) {
        jsonTemplates.add(jsonTemplate);
        return jsonTemplate;
    }

    public PostCreateTemplate setPostCreateTemplates(PostCreateTemplate postCreateTemplate) {
        postCreateTemplates.add(postCreateTemplate);
        return postCreateTemplate;
    }

    public void setJsonTemplate(Object jsonTemplate) {
        this.jsonTemplate = ObjectMapperUtil.setValue(jsonTemplate);
    }

    public void setPostCreateTemplate(Object jsonTemplate) {
        this.postCreateTemplate = ObjectMapperUtil.setValue(jsonTemplate);
    }

    public Object getJsonTemplate() {
        return ParseJson.parse(this.jsonTemplate);
    }

    public Object getPostCreateTemplate() {
        return ParseJson.parse(this.postCreateTemplate);
    }
}
