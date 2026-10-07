package OficinaMecanica.repository;

import OficinaMecanica.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Boolean existsByCpf(String cpf);
    Client findByCpf(String cpf);
}