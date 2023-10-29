package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.PostCreateTemplate;

import java.util.List;
import java.util.Optional;

public interface PostCreateTemplateRepository extends JpaRepository<PostCreateTemplate, Long> {
    List<PostCreateTemplate> findAllByTemplateTemplateId(Long templateId);
    Optional<PostCreateTemplate> findFirstByTemplateTemplateIdOrderByTimestampDesc(Long templateId);
}
