package OficinaMecanica.mapper;

import OficinaMecanica.dto.OrderServicePut;
import OficinaMecanica.dto.OrderServiceRequest;
import OficinaMecanica.dto.OrderServiceResponse;
import OficinaMecanica.entity.OrderService;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderServiceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "startedService",  ignore = true)
    @Mapping(target = "completedService", ignore = true)
    @Mapping(target = "price", ignore = true)
    OrderService toOrderService(OrderServiceRequest orderServiceRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderService(OrderServicePut orderServicePut, @MappingTarget OrderService orderService);

    OrderServiceResponse toOrderServiceResponse(OrderService orderService);
}
