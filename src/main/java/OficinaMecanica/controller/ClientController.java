package OficinaMecanica.controller;

import OficinaMecanica.dto.CarResponse;
import OficinaMecanica.dto.ClientRequest;
import OficinaMecanica.dto.ClientResponse;
import OficinaMecanica.service.CarService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ClientResponse> addClient(@RequestBody ClientRequest clientRequest){
        return new ResponseEntity<>(clientService.addClient(clientRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAllClients(){
        return ResponseEntity.ok(clientService.findAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findClientById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.findClientById(id));
    }

    @GetMapping("/{id}/cars")
    public ResponseEntity<List<CarResponse>> findAllCarsClient(@PathVariable Long id){
        return ResponseEntity.ok(carService.findAllCarsClient(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @RequestBody ClientRequest clientRequest){
        return ResponseEntity.ok(clientService.updateClient(id,clientRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientResponse> deleteClient(@PathVariable Long id){
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }


}
