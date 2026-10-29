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
public class OrderServiceRequest {
    @NotBlank(message = "descrição do serviço obrigatória.")
    private String description;
    @NotNull(message = "carro obrigatório.")
    private Long carId;

}
