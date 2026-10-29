package OficinaMecanica.repository;

import OficinaMecanica.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByTelephone(String telephone);
    boolean existsByCpfAndIdNot(String cpf, Long id);
    boolean existsByTelephoneAndIdNot(String telephone, Long id);
    Client findByCpf(String cpf);
}