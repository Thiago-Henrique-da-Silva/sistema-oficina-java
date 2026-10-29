package OficinaMecanica.service;

import OficinaMecanica.dto.ServiceHistoryResponse;
import OficinaMecanica.entity.*;
import OficinaMecanica.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import OficinaMecanica.mapper.ServiceHistoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import OficinaMecanica.repository.ServiceHistoryRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceHistoryService {
    private final ClientService clientService;
    private final CarService carService;
    private final ServiceHistoryRepository serviceHistoryRepository;
    private final ServiceHistoryMapper serviceHistoryMapper;

    @Transactional
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
                        throw new BusinessException("Defina o valor de todos os serviços antes de concluir o reparo.");
                    }

                    servicesCar.add(new  ListOfServiceHistory(order.getDescription(),  order.getPrice()));
                    totalPrice = totalPrice.add(order.getPrice());
                    order.setCalculated(true);
                }

            } else {
                throw new BusinessException("Ainda há serviços para serem finalizados.");
            }
        }

        if (servicesCar.isEmpty()){
            throw new BusinessException("Não há serviços novos para registrar no histórico deste veículo.");
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

    public Page<ServiceHistoryResponse> findAllServiceHistoryByCpf(String cpf, Pageable pageable){
        return serviceHistoryRepository.findByCpfClient(cpf, pageable)
                .map(serviceHistoryMapper::toServiceHistoryResponse);
    }

    public Page<ServiceHistoryResponse> findAllServicesHistory(Pageable pageable){
        return serviceHistoryRepository.findAll(pageable)
                .map(serviceHistoryMapper::toServiceHistoryResponse);
    }



}
