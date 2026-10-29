package OficinaMecanica.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

// @Embeddable: marca essa classe como "sem identidade própria" — ela não tem @Id,
// não é uma tabela independente, e só existe dentro de outra entidade que a contém.
// Os campos dela (descriptionService, priceService) são salvos como colunas extras
// na tabela de quem a usa (aqui, dentro da tabela criada pelo @ElementCollection
// do ServiceHistory). Sem @Id, sem repository, sem busca direta no banco —
// é só um "pacote de dados" reaproveitável dentro de outras entidades.

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ListOfServiceHistory {
    private String descriptionService;
    private BigDecimal priceService;
}
