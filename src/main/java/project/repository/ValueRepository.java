package project.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Value;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "createValue")
    List<Value> findAllByServiceId(Long serviceId);

}
