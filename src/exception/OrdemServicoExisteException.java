package exception;

public class OrdemServicoExisteException extends RuntimeException {
    public OrdemServicoExisteException(String message) {
        super(message);
    }
}
