package exception;

public class ListaServicosVazia extends RuntimeException {
    public ListaServicosVazia(String message) {
        super(message);
    }
}
