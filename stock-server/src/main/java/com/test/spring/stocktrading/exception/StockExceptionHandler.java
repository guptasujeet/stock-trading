package com.test.spring.stocktrading.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StockExceptionHandler {

    @ExceptionHandler(StockCreationException.class)
    public ProblemDetail handleStockCreationException(StockCreationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Stock could not be created");
        return problemDetail;
    }

    @ExceptionHandler(StockNotFoundException.class)
    public ProblemDetail handleStockNotFoundException(StockNotFoundException ex) {
        return ex.asProblemDetail();
    }

}
