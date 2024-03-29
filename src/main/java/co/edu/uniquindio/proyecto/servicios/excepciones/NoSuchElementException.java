package co.edu.uniquindio.proyecto.servicios.excepciones;

public class NoSuchElementException extends RuntimeException {

    public NoSuchElementException(String message) {
        super("No existe el cliente o recurso");
    }
}
