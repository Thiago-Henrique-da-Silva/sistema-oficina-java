package OficinaMecanica.mapper;

import OficinaMecanica.dto.ClientRequest;
import OficinaMecanica.dto.ClientResponse;
import OficinaMecanica.entity.Client;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cars",  ignore = true)
    Client toClient(ClientRequest clientRequest);

    @InheritConfiguration(name = "toClient")
    void updateClient(ClientRequest clientRequest, @MappingTarget Client client);

    ClientResponse toClientResponse(Client client);

    List<ClientResponse> listAllClients(List<Client> clients);


}
