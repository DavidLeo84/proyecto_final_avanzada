package co.edu.uniquindio.proyecto.servicios.excepciones;

public class ResourceInvalidException extends RuntimeException{

    public ResourceInvalidException(String message) {
        super(message);
    }
}
