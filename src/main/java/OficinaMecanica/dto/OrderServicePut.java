package OficinaMecanica.dto;

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
    private String description;
    private Long carId;
    private BigDecimal price;

}
