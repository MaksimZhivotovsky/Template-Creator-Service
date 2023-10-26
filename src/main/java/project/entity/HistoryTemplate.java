package project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(of = {"requestData"})
@EqualsAndHashCode(of = "requestDataId")
@NoArgsConstructor
@Table(name = "history_template")
public class HistoryTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_data_id")
    private Long requestDataId;

    @Column(name = "history_json_value")
    private String historyJsonValue;

    @Column(name = "is_archive")
    private Boolean isArchive = false;

    @Column(name = "timestamp")
    private Date timestamp = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    @JsonBackReference
    private Template template;
}
