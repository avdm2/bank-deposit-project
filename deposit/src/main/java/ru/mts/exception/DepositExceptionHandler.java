package ru.mts.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DepositExceptionHandler {

    @ExceptionHandler(DepositDeletingException.class)
    public ResponseEntity<String> handleClosedDepositException(DepositDeletingException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(NotEnoughFundsException.class)
    public ResponseEntity<String> handleNotEnoughFundsException(NotEnoughFundsException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(IllegalDepositFundsException.class)
    public ResponseEntity<String> handleIllegalDepositFundsException(IllegalDepositFundsException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
