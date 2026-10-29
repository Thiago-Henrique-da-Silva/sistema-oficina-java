package OficinaMecanica.repository;

import OficinaMecanica.entity.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderServiceRepository extends JpaRepository<OrderService, Long> {

    List<OrderService> findAllByCarId(Long carId);
    Page<OrderService> findAllByCarId(Long carId, Pageable pageable);


}
