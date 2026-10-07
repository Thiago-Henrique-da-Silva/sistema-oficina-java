package OficinaMecanica.controller;

import OficinaMecanica.dto.ServiceHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import OficinaMecanica.service.ServiceHistoryService;

import java.util.List;

@RestController
@RequestMapping("/historys")
@RequiredArgsConstructor
public class ServiceHistoryController {
    private final ServiceHistoryService serviceHistoryService;

    @PostMapping("/{idCar}")
    public ResponseEntity<ServiceHistoryResponse> completeRepair(@PathVariable Long idCar){
        return new ResponseEntity<>(serviceHistoryService.completeTheRepair(idCar), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServiceHistoryResponse>> getAllServicesHistory(){
        return ResponseEntity.ok(serviceHistoryService.findAllServicesHistory());
    }

    @GetMapping("/{cpf}/client")
    public ResponseEntity<List<ServiceHistoryResponse>> getAllServicesHistoryByClient(@PathVariable String cpf){
        return ResponseEntity.ok(serviceHistoryService.findAllServiceHistoryByCpf(cpf));
    }
}
