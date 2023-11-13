package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Template;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findAllByOrganizationId(Long serviceId);
    Optional<Template> findByName(String name);
}
