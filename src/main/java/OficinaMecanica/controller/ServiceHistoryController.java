package OficinaMecanica.controller;

import OficinaMecanica.dto.ServiceHistoryResponse;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import OficinaMecanica.service.ServiceHistoryService;

import java.util.List;

@RestController
@RequestMapping("/histories")
@RequiredArgsConstructor
public class ServiceHistoryController {
    private final ServiceHistoryService serviceHistoryService;

    @PostMapping("/{idCar}")
    public ResponseEntity<ServiceHistoryResponse> completeRepair(@PathVariable Long idCar){
        return new ResponseEntity<>(serviceHistoryService.completeTheRepair(idCar), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ServiceHistoryResponse>> getAllServicesHistory(Pageable pageable){
        return ResponseEntity.ok(serviceHistoryService.findAllServicesHistory(pageable));
    }

    @GetMapping("/{cpf}/client")
    public ResponseEntity<Page<ServiceHistoryResponse>> getAllServicesHistoryByClient(
            @PathVariable @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 números.") String cpf,
            Pageable pageable){
        return ResponseEntity.ok(serviceHistoryService.findAllServiceHistoryByCpf(cpf, pageable));
    }
}
