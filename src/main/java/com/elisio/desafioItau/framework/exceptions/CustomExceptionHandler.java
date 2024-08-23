package com.elisio.desafioItau.framework.exceptions;


import com.amazonaws.services.sns.model.AmazonSNSException;
import com.elisio.desafioItau.framework.adapter.in.dtos.ErrosDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.ConnectException;
import java.util.Date;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({AmazonSNSException.class, BookException.class, SendAwsSNSException.class, HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleBookException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        ErrosDetail errosDetail = new ErrosDetail(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errosDetail, HttpStatus.BAD_REQUEST);
    }

}
