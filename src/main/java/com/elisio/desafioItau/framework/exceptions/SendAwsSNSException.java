package com.elisio.desafioItau.framework.exceptions;

public class SendAwsSNSException extends RuntimeException{
    public SendAwsSNSException(String message) {
        super(message);
    }
}
