package project.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.dto.UserRcDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRcSQLRepository {
    @PersistenceContext
    private EntityManager em;

    public UserRcDto findUserByKcId(String keycloakId) {

        String sql = "select u.id, u.first_name, u.last_name, u.middle_name, u.keycloak_id, u.organization_id from user_sc u where u.keycloak_id = :keycloakId ";

        Query query = em.createNativeQuery(sql, UserRcDto.class);
        query.setParameter("keycloakId", keycloakId);


        return (UserRcDto) query.getSingleResult();
    }

    public Object object(String keycloakId) {

        String sql = "select u.id, u.first_name, u.last_name, u.middle_name, u.keycloak_id, u.organization_id from user_sc u where u.keycloak_id = :keycloakId ";

        Query query = em.createNativeQuery(sql, UserRcDto.class);
        query.setParameter("keycloakId", keycloakId);

        UserRcDto result = (UserRcDto) query.getSingleResult();


        return result;
    }
}
