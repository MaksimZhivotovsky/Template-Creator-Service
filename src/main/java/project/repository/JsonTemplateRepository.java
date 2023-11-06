package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.JsonTemplate;

import java.util.List;
import java.util.Optional;

public interface JsonTemplateRepository extends JpaRepository<JsonTemplate, Long> {
    List<JsonTemplate> findAllByTemplateTemplateId(Long templateId);

    Optional<JsonTemplate> findFirstByTemplateTemplateIdOrderByTimestampDesc(Long templateId);
}
