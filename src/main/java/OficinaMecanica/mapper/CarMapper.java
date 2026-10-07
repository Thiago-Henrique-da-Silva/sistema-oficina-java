package OficinaMecanica.mapper;

import OficinaMecanica.dto.CarRequest;
import OficinaMecanica.dto.CarResponse;
import OficinaMecanica.entity.Car;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Car toCar(CarRequest carRequest);

    @InheritConfiguration(name = "toCar")
    void  updateCar(CarRequest carRequest, @MappingTarget Car car);

    CarResponse toCarResponse(Car car);

    List<CarResponse> toCarResponseList(List<Car> cars);
}
