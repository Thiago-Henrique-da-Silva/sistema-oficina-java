package OficinaMecanica.mapper;

import OficinaMecanica.dto.ClientPut;
import OficinaMecanica.dto.ClientRequest;
import OficinaMecanica.dto.ClientResponse;
import OficinaMecanica.entity.Client;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cars",  ignore = true)
    Client toClient(ClientRequest clientRequest);

    @InheritConfiguration(name = "toClient")
    void updateClient(ClientPut clientPut, @MappingTarget Client client);

    ClientResponse toClientResponse(Client client);
}
