package co.edu.uniquindio.proyecto.servicios.excepciones;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
