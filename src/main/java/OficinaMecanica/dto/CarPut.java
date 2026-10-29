package OficinaMecanica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarPut {
    @NotBlank(message = "marca obrigatório.")
    private String brand;
    @NotBlank(message = "modelo obrigatório.")
    private String model;
    @NotBlank(message = "placa obrigatório.")
    private String plate;
    @NotNull(message = "cliente obrigatório.")
    private Long clientId;
}

