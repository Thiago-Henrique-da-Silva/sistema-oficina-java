package OficinaMecanica.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

// @ElementCollection: cria uma tabela separada no banco (ex: service_history_order_services)
// só pra guardar essa lista, vinculada ao ServiceHistory por uma chave estrangeira.
// Diferente de @OneToMany, os itens da lista NÃO são uma entidade própria (sem @Id,
// sem repository, sem existência independente) — são só dados embutidos (@Embeddable)
// que pertencem exclusivamente a este ServiceHistory. Se o ServiceHistory for apagado,
// as linhas dessa tabela somem junto (não tem como "reaproveitar" um item em outro registro).

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