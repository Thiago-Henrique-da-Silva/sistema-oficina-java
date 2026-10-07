package OficinaMecanica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private Long id;
    private String name;
    private String cpf;
    private String telephone;
}
