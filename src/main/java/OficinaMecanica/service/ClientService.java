package OficinaMecanica.service;

import OficinaMecanica.dto.ClientPut;
import OficinaMecanica.dto.ClientRequest;
import OficinaMecanica.dto.ClientResponse;
import OficinaMecanica.entity.Car;
import OficinaMecanica.entity.Client;
import OficinaMecanica.exception.ObjectHasAssociatedAttributesException;
import OficinaMecanica.exception.DataAlreadyRegisteredException;
import OficinaMecanica.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import OficinaMecanica.mapper.ClientMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import OficinaMecanica.repository.CarRepository;
import OficinaMecanica.repository.ClientRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final CarRepository carRepository;

    @Transactional
    public ClientResponse addClient(ClientRequest clientRequest){
        if (clientRepository.existsByCpf(clientRequest.getCpf())) {
            throw new DataAlreadyRegisteredException("CPF já cadastrado.");
        }

        if (clientRepository.existsByTelephone(clientRequest.getTelephone())) {
            throw new DataAlreadyRegisteredException("Telefone já cadastrado.");
        }

        Client client = clientMapper.toClient(clientRequest);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toClientResponse(savedClient);
    }

    public Page<ClientResponse> findAllClients(Pageable pageable){
        return clientRepository.findAll(pageable).map(clientMapper::toClientResponse);
    }

    public Client findClientByIdEntity(Long id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com id " + id + " não encontrado."));
    }

    public ClientResponse findClientById(Long id){
        Client  client = findClientByIdEntity(id);
        return clientMapper.toClientResponse(client);
    }

    @Transactional
    public ClientResponse updateClient(Long id, ClientPut clientPut){
        Client client = findClientByIdEntity(id);

        if (clientRepository.existsByCpfAndIdNot(clientPut.getCpf(), id)){
            throw new DataAlreadyRegisteredException("CPF já cadastrado.");
        }

        if (clientRepository.existsByTelephoneAndIdNot(clientPut.getTelephone(), id)){
            throw new DataAlreadyRegisteredException("Telefone já cadastrado.");
        }

        clientMapper.updateClient(clientPut, client);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toClientResponse(savedClient);
    }

    @Transactional
    public void deleteClientById(Long id){
        Client client = findClientByIdEntity(id);
        List<Car> cars = carRepository.findByClientId(client.getId());

        if(!cars.isEmpty()){
            throw new ObjectHasAssociatedAttributesException("Remova os veiculos deste cliente para exclui-lo.");
        }

        clientRepository.delete(client);
    }


}
