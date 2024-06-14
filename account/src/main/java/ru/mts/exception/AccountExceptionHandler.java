package ru.mts.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(NotEnoughFundsException.class)
    public ResponseEntity<String> handleNotEnoughFundsException(NotEnoughFundsException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<String> handleAccountAlreadyExistsException(AccountAlreadyExistsException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(RequireFieldsException.class)
    public ResponseEntity<String> handleRequireFieldsException(RequireFieldsException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(IllegalAccountException.class)
    public ResponseEntity<String> handleIllegalAccountException(IllegalAccountException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }
}
