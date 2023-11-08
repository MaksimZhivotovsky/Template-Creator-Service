package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Value;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Long> {
    List<Value> findAllByServiceId(Long serviceId);

}
