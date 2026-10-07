package OficinaMecanica.dto;

import OficinaMecanica.entity.ListOfServiceHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceHistoryResponse {
    private Long id;
    private String nameClient;
    private String cpfClient;
    private String telephoneClient;
    private String carBrand;
    private String carModel;
    private String carPlate;
    private List<ListOfServiceHistory> orderServices;
    private BigDecimal totalPrice;
}
