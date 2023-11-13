package project.entity;

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
@EqualsAndHashCode(of = {"id"})
@Table(name = "templates")
@JsonInclude(JsonInclude.Include.NON_NULL)


//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Template implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "ID Template")
    private Long id;

    @Column(name = "name")
    @Schema(description = "имяTemplate")
    private String name;

    @Column(name = "organization_id")
    @Schema(description = "ID организации к которому относится Template")
    private Long organizationId;

    @Column(name = "create_data")
    @Schema(description = "Время создания")
    private LocalDateTime createData;

    @Column(name = "modify_data")
    @Schema(description = "Время обновления")
    private LocalDateTime modifyData;

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
//    @JsonBackReference
    private List<Value> values = new ArrayList<>();

}
