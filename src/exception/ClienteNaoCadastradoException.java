package exception;

public class ClienteNaoCadastradoException extends RuntimeException {
    public ClienteNaoCadastradoException(String message) {
        super(message);
    }
}
