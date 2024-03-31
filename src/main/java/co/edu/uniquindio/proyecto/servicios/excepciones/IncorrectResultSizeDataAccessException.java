package co.edu.uniquindio.proyecto.servicios.excepciones;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;

public class IncorrectResultSizeDataAccessException extends DataAccessException {

    public IncorrectResultSizeDataAccessException(String message) {
        super(message);
    }
}
