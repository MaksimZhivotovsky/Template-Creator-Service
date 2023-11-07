package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Value;

import java.util.Optional;

public interface ValueRepository extends JpaRepository<Value, Long> {
//    Optional<Value> findByName(String name);

}
