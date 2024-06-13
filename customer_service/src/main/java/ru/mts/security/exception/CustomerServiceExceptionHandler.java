package ru.mts.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.mts.common.exception.RequireFieldsException;

@ControllerAdvice
public class CustomerServiceExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(RequireFieldsException.class)
    public ResponseEntity<String> handleRequireFieldsException(RequireFieldsException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
