package project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"templateId"})
@Table(name = "templates")
@JsonInclude(JsonInclude.Include.NON_NULL)


//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Template implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "is_archive")
    @Schema(description = "Флаг находится ли  шаблон в архиве")
    private Boolean isArchive;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "template_values",
            joinColumns = {@JoinColumn(name = "template_id")},
            inverseJoinColumns = {@JoinColumn(name = "value_id")})
    @JsonManagedReference
    @JsonBackReference
    private List<Value> values = new ArrayList<>();

}
