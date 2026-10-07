package OficinaMecanica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarRequest {
    private String brand;
    private String model;
    private String plate;
    private Long clientId;
}
