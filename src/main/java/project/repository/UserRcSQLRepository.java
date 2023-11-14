package project.repository;

import project.dto.UserRcDto;

public interface UserRcSQLRepository {

    UserRcDto findUserByKcId(String keycloakId);
}
