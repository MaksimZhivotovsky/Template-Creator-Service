package project.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Template;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {
//    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "createValue")
    List<Template> findAllByServiceId(Long templateId);

}
