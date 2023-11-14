package project.service;

import project.dto.ValueDto;
import project.entity.Value;

import java.util.List;

public interface ValueService {

    List<Value> getAllValuesByOrganizationId(Long organizationId);

    Value createValue(String keycloakId, ValueDto valueDto);

    Value updateValue(String keycloakId, Long valueId, ValueDto valueDto);

    void deleteByIdValue(String keycloakId, Long valueId);

    List<ValueDto> getAllByServerId(Long serverId);

    List<String> getAllJsonValueByOrganization(Long organizationId);

    List<String> getAllUpdateValueByOrganizationId(Long organizationId);

    List<Object> getAllJsonValueByServiceId( Long serviceId);

    List<Object> getAllUpdateValueByServiceId( Long serviceId);

}
