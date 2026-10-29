package OficinaMecanica.mapper;

import OficinaMecanica.dto.ServiceHistoryResponse;
import OficinaMecanica.entity.ServiceHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceHistoryMapper {

    ServiceHistoryResponse toServiceHistoryResponse(ServiceHistory serviceHistory);
}
