package OficinaMecanica.handler;

import OficinaMecanica.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;

// A exceção diz o que aconteceu; o handler conta isso para o cliente.
// @RestControllerAdvice: trata exceções de todos os controllers e devolve JSON.
// extends ResponseEntityExceptionHandler: a classe pai já trata os erros do Spring
// (validação, JSON quebrado, 405...); aqui só sobrescrevemos o que queremos mudar.
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // Erros de validação do @Valid (@NotBlank, @Pattern...). Exceção lançada pelo Spring.
    // Monta a lista de campos inválidos e personaliza o ProblemDetail que já vem pronto (400).
    // @Override: a assinatura vem da classe pai; o Spring preenche os parâmetros.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request){

        List<InvalidField> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new InvalidField(error.getField(), error.getDefaultMessage()))
                .toList();

        ProblemDetail problemDetail = ex.getBody();
        problemDetail.setTitle("Campos inválidos");
        problemDetail.setDetail("Um ou mais campos estão inválidos.");
        problemDetail.setProperty("erros", errors);

        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    // Funil: todos os erros tratados pela classe pai passam por aqui antes de virar resposta.
    // "Exception ex" aceita qualquer exceção (polimorfismo). O super monta a resposta normal
    // do Spring, e só acrescentamos o timestamp para todos os erros ficarem no mesmo formato.
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode statusCode, WebRequest request) {

        ResponseEntity<Object> response = super.handleExceptionInternal(ex, body, headers, statusCode, request);

        if (response != null && response.getBody() instanceof ProblemDetail problemDetail) {
            problemDetail.setProperty("timestamp", Instant.now());
        }
        return response;
    }

    // 404: registro buscado pelo id não existe (lançada no service com orElseThrow).
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex){
        log.warn("Recurso não encontrado: {}", ex.getMessage());

        return createProblemDetail(HttpStatus.NOT_FOUND, "Recurso não encontrado.", ex.getMessage());
    }

    // 409: dado que já existe em outro registro (CPF, telefone, placa), verificado com existsBy no service.
    @ExceptionHandler(DataAlreadyRegisteredException.class)
    public ProblemDetail handleDataAlreadyRegistered(DataAlreadyRegisteredException ex){
        log.warn("Dado já cadastrado: {}", ex.getMessage());

        return createProblemDetail(HttpStatus.CONFLICT, "Dados já cadastrados.", ex.getMessage());
    }

    // 409: exclusão bloqueada porque o registro ainda tem vínculos (cliente com carros, carro com serviços pendentes).
    @ExceptionHandler(ObjectHasAssociatedAttributesException.class)
    public ProblemDetail handleObjectHasAssociatedAttributes(ObjectHasAssociatedAttributesException ex){
        log.warn("Objeto com associações: {}", ex.getMessage());

        return createProblemDetail(HttpStatus.CONFLICT, "Objetos associados.", ex.getMessage());
    }

    // 422: os dados estão certos, mas uma regra do sistema não permite a ação (ex.: cancelar ordem concluída).
    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusiness(BusinessException ex){
        log.warn("Regra de negócio violada: {}", ex.getMessage());

        return createProblemDetail(HttpStatus.UNPROCESSABLE_CONTENT, "Regra de negócio violada.", ex.getMessage());
    }

    // 409: rede de segurança do banco (unique, chave estrangeira, nullable), para o que escapar dos ifs
    // do service. A mensagem do banco é técnica: vai só para o log, e o cliente recebe uma mensagem fixa.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException ex){
        log.error("Violação de integridade no banco.", ex);

        return createProblemDetail(HttpStatus.CONFLICT, "Conflito de dados.",
                "Os dados informados entram em conflito com um registro existente.");
    }

    // 500: rede de segurança geral para qualquer erro inesperado (bug). O detalhe vai para o log
    // e o cliente recebe uma mensagem genérica, sem expor informações internas.
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception ex){
        log.error("Erro inesperado", ex);

        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno.",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.");
    }

    // Monta o ProblemDetail com status, título, mensagem e timestamp, para não repetir código.
    // Retornar ProblemDetail direto basta: o Spring usa o status que está dentro dele.
    private ProblemDetail createProblemDetail(HttpStatus status, String title, String detail) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
