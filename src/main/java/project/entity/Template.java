package project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "templateId")
@ToString(of = {"templateId","name"})
@Table(name = "templates")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Template {

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
    private List<JsonTemplate> jsonTemplates;

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PostCreateTemplate> postCreateTemplates;

    public JsonTemplate setJsonTemplates(JsonTemplate jsonTemplate) {
        jsonTemplates.add(jsonTemplate);
        return jsonTemplate;
    }

    public PostCreateTemplate setPostCreateTemplates(PostCreateTemplate postCreateTemplate) {
        postCreateTemplates.add(postCreateTemplate);
        return postCreateTemplate;
    }
}
