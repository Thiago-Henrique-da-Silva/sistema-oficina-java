package OficinaMecanica.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ListOfServiceHistory {
    private String descriptionService;
    private BigDecimal priceService;
}
