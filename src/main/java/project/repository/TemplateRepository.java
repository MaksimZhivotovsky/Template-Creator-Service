package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
