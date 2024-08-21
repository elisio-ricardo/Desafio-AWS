package com.elisio.desafioItau.framework.config.teste;


import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SqsMessageListener {

    @SqsListener("book-queue")
    public void queueListener(String message) {
        try {
            System.out.println("Cheguei aqui");
            log.info(message);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }



}