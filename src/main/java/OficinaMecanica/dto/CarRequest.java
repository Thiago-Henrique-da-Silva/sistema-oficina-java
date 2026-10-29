package OficinaMecanica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarRequest {
    @NotBlank(message = "marca obrigatória.")
    private String brand;
    @NotBlank(message = "modelo obrigatório.")
    private String model;
    @NotBlank(message = "placa obrigatório.")
    private String plate;
    @NotNull(message = "cliente obrigatório.")
    private Long clientId;
}
