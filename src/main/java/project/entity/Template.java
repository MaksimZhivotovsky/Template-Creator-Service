package project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import project.dto.TemplateDto;
import project.mapper.TemplateMapper;
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
@ToString(of = {"templateId", "name", "jsonTemplate", "postCreateTemplate"})
@Table(name = "templates")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Шаблон")
public class Template  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    @Schema(description = "Идентификатор шаблона")
    private Long templateId;

    @Column(name = "name")
    @Schema(description = "Имя шаблона")
    private String name;

    @Column(name = "json_template")
    @Schema(description = "JSON строка для создания шаблона")
    private String jsonTemplate;

    @Column(name = "post_create_template")
    @Schema(description = "JSON строка для до создания шаблона")
    private String postCreateTemplate;

    @Column(name = "is_archive")
    @Schema(description = "Флаг находится ли шаблон в архиве")
    private Boolean isArchive;

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    @Schema(description = "Хранит историю изменений JSON строк шаблона")
    private List<JsonTemplate> jsonTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    @Schema(description = "Хранит историю изменений JSON строк для до создания шаблона")
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
