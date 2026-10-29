package OficinaMecanica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientPut {
    @NotBlank(message = "Nome obrigatório.")
    private String name;
    @NotBlank(message = "cpf obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 numeros.")
    private String cpf;
    @NotBlank(message = "telefone obrigatório.")
    private String telephone;
}
