package OficinaMecanica.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameClient;
    private String cpfClient;
    private String telephoneClient;
    private String carBrand;
    private String carModel;
    private String carPlate;
    @ElementCollection
    private List<ListOfServiceHistory> orderServices;
    private BigDecimal totalPrice;

    public ServiceHistory(String nameClient,  String cpfClient, String telephoneClient, String carBrand,
                          String carModel, String carPlate, List<ListOfServiceHistory> orderServices, BigDecimal totalPrice) {

        this.nameClient = nameClient;
        this.cpfClient = cpfClient;
        this.telephoneClient = telephoneClient;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carPlate = carPlate;
        this.orderServices = orderServices;
        this.totalPrice = totalPrice;
    }
}