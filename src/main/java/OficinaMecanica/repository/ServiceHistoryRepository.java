package OficinaMecanica.repository;

import OficinaMecanica.entity.ServiceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceHistoryRepository extends JpaRepository<ServiceHistory, Long> {

    Page<ServiceHistory> findByCpfClient(String cpf, Pageable pageable);
}
