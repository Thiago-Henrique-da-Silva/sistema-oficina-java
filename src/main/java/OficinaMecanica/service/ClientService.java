package OficinaMecanica.service;

import OficinaMecanica.dto.ClientRequest;
import OficinaMecanica.dto.ClientResponse;
import OficinaMecanica.entity.Car;
import OficinaMecanica.entity.Client;
import lombok.RequiredArgsConstructor;
import OficinaMecanica.mapper.ClientMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import OficinaMecanica.repository.CarRepository;
import OficinaMecanica.repository.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final CarRepository carRepository;

    public ClientResponse addClient(ClientRequest clientRequest){
        if (clientRequest.getName() == null || clientRequest.getCpf() == null || clientRequest.getTelephone() == null ||
                clientRequest.getName().isBlank() || clientRequest.getCpf().isBlank() || clientRequest.getTelephone().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "preencha todos os campos.");
        }

        if (clientRepository.existsByCpf(clientRequest.getCpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
        }

        Client client = clientMapper.toClient(clientRequest);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toClientResponse(savedClient);
    }

    public List<ClientResponse> findAllClients(){
        List<Client> clients = clientRepository.findAll();

        if (clients.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum cliente encontrado.");
        }

        return clientMapper.listAllClients(clients);
    }

    public Client findClientByIdEntity(Long id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum cliente encontrado."));
    }

    public ClientResponse findClientById(Long id){
        Client  client = findClientByIdEntity(id);
        return clientMapper.toClientResponse(client);
    }

    public ClientResponse updateClient(Long id, ClientRequest clientRequest){
        if (clientRequest.getName() == null || clientRequest.getCpf() == null || clientRequest.getTelephone() == null ||
                clientRequest.getName().isBlank() || clientRequest.getCpf().isBlank() ||
                clientRequest.getTelephone().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "preencha todos os campos.");
        }

        Client client = findClientByIdEntity(id);
        clientMapper.updateClient(clientRequest, client);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toClientResponse(savedClient);
    }

    public void deleteClientById(Long id){
        Client client = findClientByIdEntity(id);
        List<Car> cars = carRepository.findByClientId(client.getId());

        if(!cars.isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Remova os veiculos deste cliente para exclui-lo.");
        }

        clientRepository.delete(client);
    }


}
