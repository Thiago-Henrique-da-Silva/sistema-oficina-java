package OficinaMecanica.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record OrderServicePriceRequest(
        @NotNull(message = "Valor obrigatório.")
        @PositiveOrZero(message = "Valor não pode ser negativo.")
        BigDecimal price
) {
}
