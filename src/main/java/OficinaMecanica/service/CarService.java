package OficinaMecanica.service;
import OficinaMecanica.dto.CarRequest;
import OficinaMecanica.dto.CarResponse;
import OficinaMecanica.entity.Car;
import OficinaMecanica.entity.Client;
import OficinaMecanica.entity.OrderService;
import OficinaMecanica.entity.ServiceOrderStatus;
import lombok.RequiredArgsConstructor;
import OficinaMecanica.mapper.CarMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import OficinaMecanica.repository.CarRepository;
import OficinaMecanica.repository.OrderServiceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final ClientService clientService;
    private final OrderServiceRepository orderServiceRepository;

    public CarResponse addCar(CarRequest carRequest) {
        if (carRequest.getBrand() == null || carRequest.getModel() == null || carRequest.getPlate() == null ||
                carRequest.getClientId() == null || carRequest.getModel().isBlank() || carRequest.getPlate().isBlank() ||
                carRequest.getBrand().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Todos os campos devem ser preenchidos.");
        }

        Client client = clientService.findClientByIdEntity(carRequest.getClientId());
        Car car = carMapper.toCar(carRequest);
        car.setClient(client);
        Car savedCar = carRepository.save(car);
        return carMapper.toCarResponse(savedCar);
    }

    public List<CarResponse> findAllCars() {
        List<Car> cars = carRepository.findAll();

        if  (cars.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de carros vazia.");
        }

        return carMapper.toCarResponseList(cars);
    }

    public List<CarResponse> findAllCarsClient(Long id){
        Client client = clientService.findClientByIdEntity(id);
        List<Car> cars = client.getCars();

        if (cars.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Este cliente não possui nenhum carro.");
        }

        return carMapper.toCarResponseList(cars);
    }

    public Car findCarByIdEntity(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado"));
    }

    public CarResponse findCarById(Long id) {
        Car car = findCarByIdEntity(id);
        return carMapper.toCarResponse(car);
    }

    public CarResponse updateCar(Long id, CarRequest carRequest) {
        if (carRequest.getBrand() == null || carRequest.getModel() == null || carRequest.getPlate() == null ||
                carRequest.getClientId() == null || carRequest.getModel().isBlank() || carRequest.getPlate().isBlank() ||
                carRequest.getBrand().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Todos os campos devem ser preenchidos.");
        }

        Client client = clientService.findClientByIdEntity(carRequest.getClientId());
        Car car = findCarByIdEntity(id);
        carMapper.updateCar(carRequest, car);
        car.setClient(client);
        Car savedCar = carRepository.save(car);
        return carMapper.toCarResponse(savedCar);
    }

    public void deleteCarById(Long id) {
        Car car = findCarByIdEntity(id);
        List<OrderService> orders = orderServiceRepository.findAllByCarId(car.getId());

        for  (OrderService order : orders) {
            if(order.getServiceOrderStatus() == ServiceOrderStatus.WAITING ||
            order.getServiceOrderStatus() == ServiceOrderStatus.INITIATED) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "este carro possui serviços pendentes.");
            }
        }

        carRepository.delete(car);
    }
}
