package OficinaMecanica.service;

import OficinaMecanica.dto.OrderServicePut;
import OficinaMecanica.dto.OrderServiceRequest;
import OficinaMecanica.dto.OrderServiceResponse;
import OficinaMecanica.entity.Car;
import OficinaMecanica.entity.OrderService;
import OficinaMecanica.entity.ServiceOrderStatus;
import OficinaMecanica.exception.BusinessException;
import OficinaMecanica.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import OficinaMecanica.mapper.OrderServiceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import OficinaMecanica.repository.OrderServiceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceOrderService {
    private final OrderServiceRepository orderServiceRepository;
    private final OrderServiceMapper orderServiceMapper;
    private final CarService carService;

    @Transactional
    public OrderServiceResponse createOrderService(OrderServiceRequest orderServiceRequest){
        Car car = carService.findCarByIdEntity(orderServiceRequest.getCarId());
        OrderService orderService = new OrderService(orderServiceRequest.getDescription(), car);
        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    @Transactional
    public OrderServiceResponse startedOrderService(Long id){
        OrderService orderService = findOrderServiceByIdEntity(id);

        switch (orderService.getServiceOrderStatus()) {
            case INITIATED -> throw new BusinessException("Este serviço já foi iniciado.");
            case CANCELED -> throw new BusinessException("Este serviço foi cancelado.");
            case COMPLETED -> throw new BusinessException("Este serviço já está completo.");
        }

        orderService.setStartedService(LocalDate.now());
        orderService.setServiceOrderStatus(ServiceOrderStatus.INITIATED);
        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    @Transactional
    public OrderServiceResponse completedOrderService(Long id){

        OrderService orderService = findOrderServiceByIdEntity(id);

        switch (orderService.getServiceOrderStatus()) {
            case CANCELED -> throw new BusinessException("Este serviço foi cancelado.");
            case COMPLETED -> throw new BusinessException("Este serviço já está completo.");
            case WAITING -> throw new BusinessException("Este serviço ainda não foi iniciado.");
        }

        if (orderService.getStartedService() == null) {
            throw new BusinessException("Data de início do serviço não definida.");
        }

        orderService.setCompletedService(LocalDate.now());
        orderService.setServiceOrderStatus(ServiceOrderStatus.COMPLETED);
        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    @Transactional
    public OrderServiceResponse cancelledOrderService(Long id){
        OrderService orderService = findOrderServiceByIdEntity(id);

        switch (orderService.getServiceOrderStatus()) {
            case CANCELED -> throw new BusinessException("Este serviço já foi cancelado.");
            case COMPLETED -> throw new BusinessException("Serviços concluídos não podem ser cancelados.");
        }

        orderService.setCancelledService(LocalDate.now());
        orderService.setServiceOrderStatus(ServiceOrderStatus.CANCELED);
        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    @Transactional
    public OrderServiceResponse addPrice(Long id, BigDecimal price){
        OrderService orderService = findOrderServiceByIdEntity(id);
        orderService.setPrice(price);
        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    @Transactional
    public OrderServiceResponse updateOrderService(Long id, OrderServicePut orderServicePut){
        OrderService orderService = findOrderServiceByIdEntity(id);
        orderServiceMapper.updateOrderService(orderServicePut, orderService);

        if (orderServicePut.getCarId() != null) {
            Car car = carService.findCarByIdEntity(orderServicePut.getCarId());
            orderService.setCar(car);
        }

        OrderService savedOrderService = orderServiceRepository.save(orderService);
        return orderServiceMapper.toOrderServiceResponse(savedOrderService);
    }

    public Page<OrderServiceResponse> findAllOrdersService(Pageable pageable){
        return orderServiceRepository.findAll(pageable).map(orderServiceMapper::toOrderServiceResponse);
    }

    public Page<OrderServiceResponse> findAllOrdersCar(Long idCar, Pageable pageable){
        return orderServiceRepository.findAllByCarId(idCar, pageable).map(orderServiceMapper::toOrderServiceResponse);
    }

    public OrderService findOrderServiceByIdEntity(Long id){
        return orderServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem de serviço com id " + id + " não encontrada."));
    }

    public OrderServiceResponse findOrderServiceById(Long id){
        OrderService orderService = findOrderServiceByIdEntity(id);
        return orderServiceMapper.toOrderServiceResponse(orderService);
    }
}
