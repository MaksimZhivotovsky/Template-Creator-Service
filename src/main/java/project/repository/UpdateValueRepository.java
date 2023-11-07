package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.UpdateValue;

import java.util.List;

public interface UpdateValueRepository extends JpaRepository<UpdateValue, Long> {
    List<UpdateValue> findAllByValueValueId(Long valueId);

//    Optional<UpdateValue> findFirstByTemplateTemplateIdOrderByTimestampDesc(Long templateId);
}
