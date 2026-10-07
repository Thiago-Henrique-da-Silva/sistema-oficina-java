package OficinaMecanica.service;

import OficinaMecanica.dto.OrderServicePut;
import OficinaMecanica.dto.OrderServiceRequest;
import OficinaMecanica.dto.OrderServiceResponse;
import OficinaMecanica.entity.Car;
import OficinaMecanica.entity.OrderService;
import OficinaMecanica.entity.ServiceOrderStatus;
import lombok.RequiredArgsConstructor;
import OficinaMecanica.mapper.OrderServiceMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import OficinaMecanica.repository.OrderServiceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceOrderService {
    private final OrderServiceRepository orderServiceRepository;
    private final OrderServiceMapper orderServiceMapper;
    private final CarService carService;

    public OrderServiceResponse createOrderService(OrderServiceRequest orderServiceRequest){
        if (orderServiceRequest.getDescription() == null || orderServiceRequest.getCarId() == null ||
        orderServiceRequest.getDescription().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Todos os campos devem ser preenchidos");
        }

        Car car = carService.findCarByIdEntity(orderServiceRequest.getCarId());
        OrderService orderService = new OrderService(orderServiceRequest.getDescription(), car);
        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    public OrderServiceResponse startedOrderService(Long id, LocalDate startedService){
        if (startedService == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "data de inicio não preencida.");
        }

        OrderService orderService = findOrderServiceByIdEntity(id);

        switch (orderService.getServiceOrderStatus()) {
            case INITIATED -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Este serviço ja foi iniciado.");
            case CANCELED -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Este serviço foi cancelado.");
            case COMPLETED -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Este serviço já está completo.");
        }

        orderService.setStartedService(startedService);
        orderService.setServiceOrderStatus(ServiceOrderStatus.INITIATED);
        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    public OrderServiceResponse completedOrderService(Long id, LocalDate completedService){

        OrderService orderService = findOrderServiceByIdEntity(id);

        switch (orderService.getServiceOrderStatus()) {
            case CANCELED -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Este serviço foi cancelado.");
            case COMPLETED -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Este serviço já está completo.");
            case WAITING -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Este serviço ainda não foi iniciado;");
        }

        if (completedService == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Data de término não preencida.");
        }

        if (orderService.getStartedService() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "data inicial não definida");
        }

        if (completedService.isBefore(orderService.getStartedService())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Data de finalização deve ser após a data de inicio.");
        }

        orderService.setCompletedService(completedService);
        orderService.setServiceOrderStatus(ServiceOrderStatus.COMPLETED);
        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    public OrderServiceResponse cancelledOrderService(Long id){
        OrderService orderService = findOrderServiceByIdEntity(id);

        if (orderService.getServiceOrderStatus() == ServiceOrderStatus.CANCELED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este serviço já foi cancelado.");
        }

        if (orderService.getServiceOrderStatus() == ServiceOrderStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Serviços completos não podem ser finalizados.");
        }

        if (orderService.getStartedService() == null) {
            orderService.setStartedService(LocalDate.now());
        }

        if (orderService.getCompletedService() == null) {
            orderService.setCompletedService(LocalDate.now());
        }

        if (orderService.getPrice() == null) {
            orderService.setPrice(BigDecimal.ZERO);
        }

        orderService.setServiceOrderStatus(ServiceOrderStatus.CANCELED);
        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    public OrderServiceResponse addPrice(Long id, BigDecimal price){
        OrderService orderService = findOrderServiceByIdEntity(id);

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Valor inválido.");
        }

        orderService.setPrice(price);
        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    public OrderServiceResponse updateOrderService(Long id, OrderServicePut orderServicePut){
        Car car = carService.findCarByIdEntity(orderServicePut.getCarId());
        OrderService orderService = findOrderServiceByIdEntity(id);
        orderServiceMapper.updateOrderService(orderServicePut, orderService);
        orderService.setCar(car);
        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    public List<OrderServiceResponse> findAllOrdersService(){
        List<OrderService> orderServiceList = orderServiceRepository.findAll();
        return orderServiceMapper.toOrderServiceResponseList(orderServiceList);
    }

    public List<OrderServiceResponse> findAllOrdersCar(Long idCar){
        Car car =  carService.findCarByIdEntity(idCar);
        List<OrderService> ordersCar = car.getOrders();

        if (ordersCar.isEmpty()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de serviço vazia.");
        }

        return orderServiceMapper.toOrderServiceResponseList(ordersCar);
    }

    public OrderService findOrderServiceByIdEntity(Long id){
        return orderServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "serviço não encontrado."));
    }

    public OrderServiceResponse findOrderServiceById(Long id){
        OrderService orderService = findOrderServiceByIdEntity(id);
        return orderServiceMapper.toOrderServiceResponse(orderService);
    }
}
