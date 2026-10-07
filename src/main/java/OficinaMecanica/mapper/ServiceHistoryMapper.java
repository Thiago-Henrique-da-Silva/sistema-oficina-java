package OficinaMecanica.mapper;

import OficinaMecanica.dto.ServiceHistoryResponse;
import OficinaMecanica.entity.ServiceHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceHistoryMapper {

    ServiceHistoryResponse toServiceHistoryResponse(ServiceHistory serviceHistory);

    List<ServiceHistoryResponse> toServiceHistoryResponseList(List<ServiceHistory> serviceHistories);
}
