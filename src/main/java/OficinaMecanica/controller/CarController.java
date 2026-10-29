package OficinaMecanica.controller;

import OficinaMecanica.dto.CarPut;
import OficinaMecanica.dto.CarRequest;
import OficinaMecanica.dto.CarResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import OficinaMecanica.service.CarService;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarResponse> addCar(@RequestBody @Valid CarRequest carRequest){
        return new ResponseEntity<>(carService.addCar(carRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CarResponse>> getAllCars(Pageable pageable){
        return ResponseEntity.ok(carService.findAllCars(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> findCarById(@PathVariable Long id){
        return ResponseEntity.ok(carService.findCarById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @RequestBody @Valid CarPut carPut){
        return ResponseEntity.ok(carService.updateCar(id, carPut));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id){
        carService.deleteCarById(id);
        return ResponseEntity.noContent().build();
    }
}
