package OficinaMecanica.repository;

import OficinaMecanica.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CarRepository extends JpaRepository<Car, Long> {

    boolean existsByPlate(String plate);
    boolean existsByPlateAndIdNot(String plate, Long id);
    List<Car> findByClientId(Long id);
    Page<Car> findByClientId(Long id, Pageable pageable);
}
