package neoflexTestTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidVacationDateException extends RuntimeException {
    public InvalidVacationDateException(String message) {
        super(message);
    }
}
