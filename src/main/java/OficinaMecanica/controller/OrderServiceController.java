package OficinaMecanica.controller;

import OficinaMecanica.dto.OrderServicePriceRequest;
import OficinaMecanica.dto.OrderServicePut;
import OficinaMecanica.dto.OrderServiceRequest;
import OficinaMecanica.dto.OrderServiceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import OficinaMecanica.service.ServiceOrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderServiceController {
    private final ServiceOrderService serviceOrderService;

    @PostMapping
    public ResponseEntity<OrderServiceResponse> createOrder(@RequestBody @Valid OrderServiceRequest orderServiceRequest){
        return new ResponseEntity<>(serviceOrderService.createOrderService(orderServiceRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<OrderServiceResponse>> getAllOrders(Pageable pageable){
        return ResponseEntity.ok(serviceOrderService.findAllOrdersService(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderServiceResponse> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(serviceOrderService.findOrderServiceById(id));
    }

    @GetMapping("/{id}/car")
    public ResponseEntity<Page<OrderServiceResponse>> findAllOrdersCar(@PathVariable Long id, Pageable pageable){
        return ResponseEntity.ok(serviceOrderService.findAllOrdersCar(id, pageable));
    }

    @PatchMapping("/{id}/started")
    public ResponseEntity<OrderServiceResponse> startedOrderService(@PathVariable Long id){
        return ResponseEntity.ok(serviceOrderService.startedOrderService(id));
    }

    @PatchMapping("/{id}/completed")
    public ResponseEntity<OrderServiceResponse> completedOrderService(@PathVariable Long id){
        return ResponseEntity.ok(serviceOrderService.completedOrderService(id));
    }

    @PatchMapping("/{id}/cancelled")
    public ResponseEntity<OrderServiceResponse> cancelledOrderService(@PathVariable Long id){
        return ResponseEntity.ok(serviceOrderService.cancelledOrderService(id));
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<OrderServiceResponse> addPrice(@PathVariable Long id,
                                                         @RequestBody @Valid OrderServicePriceRequest priceRequest){
        return ResponseEntity.ok(serviceOrderService.addPrice(id, priceRequest.price()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderServiceResponse> updateOrder(@PathVariable Long id,
                                                            @RequestBody @Valid OrderServicePut orderServicePut){
        return ResponseEntity.ok(serviceOrderService.updateOrderService(id, orderServicePut));
    }




}
