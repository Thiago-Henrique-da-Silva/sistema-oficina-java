package exception;

public class ListaCarrosVaziaException extends RuntimeException {
    public ListaCarrosVaziaException(String message) {
        super(message);
    }
}
