package com.mandacarubroker.exceptions;

import com.mandacarubroker.service.exceptions.CampoNuloException;
import com.mandacarubroker.service.exceptions.CampoVazioException;
import com.mandacarubroker.service.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class StockExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError>ObjectNotFound(ObjectNotFoundException ex, HttpServletRequest request){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        StandardError error = new StandardError(LocalDateTime.now().format(formatter), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CampoVazioException.class)
    public ResponseEntity<StandardError>CampoVazioException(CampoVazioException ex, HttpServletRequest request){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        StandardError error = new StandardError(LocalDateTime.now().format(formatter), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(CampoNuloException.class)
    public ResponseEntity<StandardError>CampoNuloException(CampoNuloException ex, HttpServletRequest request){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    StandardError error = new StandardError(LocalDateTime.now().format(formatter), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
