package OficinaMecanica.controller;

import OficinaMecanica.dto.CarResponse;
import OficinaMecanica.dto.ClientPut;
import OficinaMecanica.dto.ClientRequest;
import OficinaMecanica.dto.ClientResponse;
import OficinaMecanica.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import OficinaMecanica.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final CarService carService;

    @PostMapping
    public ResponseEntity<ClientResponse> addClient(@RequestBody @Valid ClientRequest clientRequest){
        return new ResponseEntity<>(clientService.addClient(clientRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> findAllClients(Pageable pageable){
        return ResponseEntity.ok(clientService.findAllClients(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findClientById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.findClientById(id));
    }

    @GetMapping("/{id}/cars")
    public ResponseEntity<Page<CarResponse>> findAllCarsClient(@PathVariable Long id, Pageable pageable){
        return ResponseEntity.ok(carService.findAllCarsClient(id, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @RequestBody @Valid ClientPut clientPut){
        return ResponseEntity.ok(clientService.updateClient(id,clientPut));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }


}
