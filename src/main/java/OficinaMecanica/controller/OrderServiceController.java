package OficinaMecanica.controller;

import OficinaMecanica.dto.OrderServicePut;
import OficinaMecanica.dto.OrderServiceRequest;
import OficinaMecanica.dto.OrderServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import OficinaMecanica.service.ServiceOrderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderServiceController {
    private final ServiceOrderService serviceOrderService;

    @PostMapping
    public ResponseEntity<OrderServiceResponse> createOrder(@RequestBody OrderServiceRequest orderServiceRequest){
        return new ResponseEntity<>(serviceOrderService.createOrderService(orderServiceRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderServiceResponse>> getAllOrders(){
        return ResponseEntity.ok(serviceOrderService.findAllOrdersService());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderServiceResponse> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(serviceOrderService.findOrderServiceById(id));
    }

    @GetMapping("/{id}/car")
    public ResponseEntity<List<OrderServiceResponse>> findAllOrdersCar(@PathVariable Long id){
        return ResponseEntity.ok(serviceOrderService.findAllOrdersCar(id));
    }

    @PatchMapping("/{id}/started")
    public ResponseEntity<OrderServiceResponse> startedOrderService(@PathVariable Long id,
                                                                    @RequestBody LocalDate startedService){
        return ResponseEntity.ok(serviceOrderService.startedOrderService(id, startedService));
    }

    @PatchMapping("/{id}/completed")
    public ResponseEntity<OrderServiceResponse> completedOrderService(@PathVariable Long id,
                                                                      @RequestBody LocalDate completedService){
        return ResponseEntity.ok(serviceOrderService.completedOrderService(id, completedService));
    }

    @PatchMapping("/{id}/cancelled")
    public ResponseEntity<OrderServiceResponse> cancelledOrderService(@PathVariable Long id){
        return ResponseEntity.ok(serviceOrderService.cancelledOrderService(id));
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<OrderServiceResponse> addPrice(@PathVariable Long id, @RequestBody BigDecimal price){
        return ResponseEntity.ok(serviceOrderService.addPrice(id, price));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderServiceResponse> updateOrder(@PathVariable Long id,
                                                            @RequestBody OrderServicePut orderServicePut){
        return ResponseEntity.ok(serviceOrderService.updateOrderService(id, orderServicePut));
    }




}
