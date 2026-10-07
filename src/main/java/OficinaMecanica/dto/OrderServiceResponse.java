package OficinaMecanica.dto;

import OficinaMecanica.entity.ServiceOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderServiceResponse {
    private Long id;
    private String description;
    private CarResponse car;
    private LocalDate startedService;
    private LocalDate completedService;
    private BigDecimal price;
    private ServiceOrderStatus serviceOrderStatus;
}
