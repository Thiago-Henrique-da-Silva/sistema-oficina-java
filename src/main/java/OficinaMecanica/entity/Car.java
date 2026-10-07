package OficinaMecanica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String plate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
    @OneToMany(mappedBy = "car")
    private List<OrderService> orders;

}
