package OficinaMecanica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {
    private Long id;
    private String brand;
    private String model;
    private String plate;
    private ClientResponse client;
}
