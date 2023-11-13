package project.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.dto.UserRcDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BasicSQLRepository {
    @PersistenceContext
    private EntityManager em;

//    String sql = "SELECT USER.* FROM USER_ AS USER WHERE ID = ?";
//
//    Query query = em.createNativeQuery(sql, User.class);
//    query.setParameter(1, id);
//    User user = (User) query.getSingleResult();


    public Long findUserByKcId(Long keycloakId) { //  String

//        String sql = "select user_sc.id from user_sc where user_sc.keycloak_id = :keycloakId ";
//        Query query = em.createNativeQuery(sql);
//        query.setParameter("keycloakId", keycloakId);
//        Object result = query.getSingleResult();
//
//        if (result != null) {
//            return Long.parseLong(result.toString());
//        } else {
//            return null;
//        }

        String sql = "select u.id, u.first_name, u.last_name, u.middle_name, u.keycloak_id, u.organization_id from user_sc u where u.id = :keycloakId ";

        Query query = em.createNativeQuery(sql, UserRcDto.class);
        query.setParameter("id", keycloakId);
        UserRcDto userRcDto = (UserRcDto) query.getSingleResult();

        return null;
    }
}
