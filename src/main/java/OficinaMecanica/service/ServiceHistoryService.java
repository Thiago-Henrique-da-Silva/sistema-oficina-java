package OficinaMecanica.service;

import OficinaMecanica.dto.ServiceHistoryResponse;
import OficinaMecanica.entity.*;
import lombok.RequiredArgsConstructor;
import OficinaMecanica.mapper.ServiceHistoryMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import OficinaMecanica.repository.ServiceHistoryRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceHistoryService {
    private final ClientService clientService;
    private final CarService carService;
    private final ServiceHistoryRepository serviceHistoryRepository;
    private final ServiceHistoryMapper serviceHistoryMapper;

    public ServiceHistoryResponse completeTheRepair(Long idCar) {
        Car car = carService.findCarByIdEntity(idCar);
        Client client  = clientService.findClientByIdEntity(car.getClient().getId());
        List<ListOfServiceHistory> servicesCar = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderService order : car.getOrders()) {

            if (order.getServiceOrderStatus() == ServiceOrderStatus.CANCELED ||
            order.getServiceOrderStatus() == ServiceOrderStatus.COMPLETED) {

                if (!order.isCalculated()){

                    if (order.getPrice() == null) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "defina os valores dos serviços.");
                    }

                    servicesCar.add(new  ListOfServiceHistory(order.getDescription(),  order.getPrice()));
                    totalPrice = totalPrice.add(order.getPrice());
                    order.setCalculated(true);
                }

            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ainda há serviços para serem finalizados.");
            }
        }

        if (servicesCar.isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Lista de serviços vazia");
        }

        ServiceHistory serviceHistory = new ServiceHistory(
                client.getName(),
                client.getCpf(),
                client.getTelephone(),
                car.getBrand(),
                car.getModel(),
                car.getPlate(),
                servicesCar,
                totalPrice
        );

        ServiceHistory savedHistory = serviceHistoryRepository.save(serviceHistory);
        return serviceHistoryMapper.toServiceHistoryResponse(savedHistory);
    }

    public List<ServiceHistoryResponse> findAllServiceHistoryByCpf(String cpf){
        if (cpf == null || cpf.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF digitado inválido.");
        }

        List<ServiceHistory> serviceHistory = serviceHistoryRepository.findByCpfClient(cpf);

        if (serviceHistory.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Historico vazio.");
        }

        return serviceHistoryMapper.toServiceHistoryResponseList(serviceHistory);
    }

    public List<ServiceHistoryResponse> findAllServicesHistory(){
        List<ServiceHistory> serviceHistories = serviceHistoryRepository.findAll();
        return serviceHistoryMapper.toServiceHistoryResponseList(serviceHistories);
    }



}
