package example.employee.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global error handler.
 */
@ControllerAdvice
public class GlobalErrorHandler {

    /**
     * Handles invalid cursor exceptions.
     *
     * @param e invalid cursor exception
     * @return an HTTP 400 response
     */
    @ExceptionHandler(value = InvalidCursorException.class)
    public ResponseEntity<?> handleInvalidCursorException(InvalidCursorException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
