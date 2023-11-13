package project.service;

import project.dto.ValueDto;
import project.entity.Value;

import java.util.List;
import java.util.Optional;

public interface ValueService {

    List<ValueDto> getAllValues(Long keycloakId);

    Optional<ValueDto> getByIdValue(Long keycloakId,Long valueId);

    Value createValue(Long keycloakId, ValueDto valueDto);

    Value updateValue(Long keycloakId, Long valueId, ValueDto valueDto);

    void deleteByIdValue(Long keycloakId, Long valueId);

    List<ValueDto> getAllByServerId( Long serverId);

    List<String> getAllJsonValueByOrganization(Long organizationId);

    List<String> getAllUpdateValueByOrganizationId(Long organizationId);

    List<Object> getAllJsonValueByServiceId( Long serviceId);

    List<Object> getAllUpdateValueByServiceId( Long serviceId);

}
