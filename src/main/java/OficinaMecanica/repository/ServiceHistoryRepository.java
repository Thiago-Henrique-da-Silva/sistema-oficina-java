package OficinaMecanica.repository;

import OficinaMecanica.entity.ServiceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceHistoryRepository extends JpaRepository<ServiceHistory, Long> {

    List<ServiceHistory> findByCpfClient(String cpf);
}
