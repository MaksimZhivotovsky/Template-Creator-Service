package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.HistoryTemplate;

import java.util.List;
import java.util.Optional;

public interface HistoryTemplateRepository extends JpaRepository<HistoryTemplate, Long> {
    List<HistoryTemplate> findAllByTemplateTemplateId(Long templateId);
    Optional<HistoryTemplate> findFirstByTemplateTemplateIdOrderByTimestampDesc(Long templateId);
//    RequestData findFirstByTimestampOrderByTimestampDesc(Long templateId);
}
