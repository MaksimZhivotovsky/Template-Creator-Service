package project.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.dto.UserRcDto;
import project.exceptions.UserCheckExceptions;
import project.repository.UserRcSQLRepository;

@Component
public class CheckUser {

    private static UserRcSQLRepository userRcSQLRepository;
    private CheckUser() {}

    @Autowired
    public void setUserRcSQLRepository(UserRcSQLRepository userRcSQLRepository) {
        CheckUser.userRcSQLRepository = userRcSQLRepository;
    }

    public static void check(String keycloakId, Long organizationId) {
        UserRcDto userRcDto = userRcSQLRepository.findUserByKcId(keycloakId);
        if(!userRcDto.getOrganizationId().equals(organizationId)) {
            throw new UserCheckExceptions("Нет такой возможности у пользователя : " + userRcDto);
        }
    }
}
