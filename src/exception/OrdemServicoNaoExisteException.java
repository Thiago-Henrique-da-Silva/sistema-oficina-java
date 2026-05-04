package exception;

public class OrdemServicoNaoExisteException extends RuntimeException {
    public OrdemServicoNaoExisteException(String message) {
        super(message);
    }
}
