package OficinaMecanica.service;
import OficinaMecanica.dto.CarPut;
import OficinaMecanica.dto.CarRequest;
import OficinaMecanica.dto.CarResponse;
import OficinaMecanica.entity.Car;
import OficinaMecanica.entity.Client;
import OficinaMecanica.entity.OrderService;
import OficinaMecanica.entity.ServiceOrderStatus;
import OficinaMecanica.exception.DataAlreadyRegisteredException;
import OficinaMecanica.exception.ObjectHasAssociatedAttributesException;
import OficinaMecanica.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import OficinaMecanica.mapper.CarMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import OficinaMecanica.repository.CarRepository;
import OficinaMecanica.repository.OrderServiceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final ClientService clientService;
    private final OrderServiceRepository orderServiceRepository;

    @Transactional
    public CarResponse addCar(CarRequest carRequest) {
        if (carRepository.existsByPlate(carRequest.getPlate())) {
            throw new DataAlreadyRegisteredException("Já existe um veículo cadastrado com esta placa.");
        }

        Client client = clientService.findClientByIdEntity(carRequest.getClientId());
        Car car = carMapper.toCar(carRequest);
        car.setClient(client);
        Car savedCar = carRepository.save(car);
        return carMapper.toCarResponse(savedCar);
    }

    public Page<CarResponse> findAllCars(Pageable pageable) {
        return carRepository.findAll(pageable).map(carMapper::toCarResponse);
    }

    public Page<CarResponse> findAllCarsClient(Long id, Pageable pageable) {
        return carRepository.findByClientId(id, pageable).map(carMapper::toCarResponse);
    }

    public Car findCarByIdEntity(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo com id " + id + " não encontrado."));
    }

    public CarResponse findCarById(Long id) {
        Car car = findCarByIdEntity(id);
        return carMapper.toCarResponse(car);
    }

    @Transactional
    public CarResponse updateCar(Long id, CarPut carPut) {
        Car car = findCarByIdEntity(id);

        if(carRepository.existsByPlateAndIdNot(carPut.getPlate(), id)) {
            throw new DataAlreadyRegisteredException("Já existe um veículo cadastrado com esta placa.");
        }

        Client client = clientService.findClientByIdEntity(carPut.getClientId());
        carMapper.updateCar(carPut, car);
        car.setClient(client);
        Car savedCar = carRepository.save(car);
        return carMapper.toCarResponse(savedCar);
    }

    @Transactional
    public void deleteCarById(Long id) {
        Car car = findCarByIdEntity(id);
        List<OrderService> orders = orderServiceRepository.findAllByCarId(car.getId());

        if (orders.stream().anyMatch(order -> order.getServiceOrderStatus() == ServiceOrderStatus.WAITING ||
                order.getServiceOrderStatus() == ServiceOrderStatus.INITIATED)) {
            throw new ObjectHasAssociatedAttributesException("Este carro possui serviços pendentes.");
        }
        
        carRepository.delete(car);
    }
}
