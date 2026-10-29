package OficinaMecanica.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderServicePut {
    // campos opcionais: o que vier nulo é mantido (IGNORE no mapper); se vier, é validado
    @Pattern(regexp = ".*\\S.*", message = "descrição não pode ser vazia.")
    private String description;
    private Long carId;
    @PositiveOrZero(message = "Valor não pode ser negativo.")
    private BigDecimal price;
}
