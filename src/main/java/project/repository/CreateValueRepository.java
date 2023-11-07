package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.CreateValue;

import java.util.List;

public interface CreateValueRepository extends JpaRepository<CreateValue, Long> {
    List<CreateValue> findAllByValueValueId(Long templateId);
//
//    Optional<CreateValue> findFirstByTemplateTemplateIdOrderByTimestampDesc(Long templateId);
}
