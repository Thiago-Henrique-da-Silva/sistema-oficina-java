package OficinaMecanica.controller;

import OficinaMecanica.dto.CarRequest;
import OficinaMecanica.dto.CarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import OficinaMecanica.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarResponse> addCar(@RequestBody CarRequest carRequest){
        return new ResponseEntity<>(carService.addCar(carRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars(){
        return ResponseEntity.ok(carService.findAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> findCarById(@PathVariable Long id){
        return ResponseEntity.ok(carService.findCarById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @RequestBody CarRequest carRequest){
        return ResponseEntity.ok(carService.updateCar(id, carRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarResponse> deleteCar(@PathVariable Long id){
        carService.deleteCarById(id);
        return ResponseEntity.noContent().build();
    }
}
