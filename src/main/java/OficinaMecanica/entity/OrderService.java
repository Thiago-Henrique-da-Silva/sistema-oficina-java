package OficinaMecanica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;
    private LocalDate startedService;
    private LocalDate completedService;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ServiceOrderStatus serviceOrderStatus;
    private boolean calculated;

    public OrderService(String description, Car car) {
        this.description = description;
        this.car = car;
        this.serviceOrderStatus = ServiceOrderStatus.WAITING;
    }
}
