package com.elisio.desafioItau.framework.adapter.in.dtos;


import lombok.Data;

import java.util.Date;

@Data
public class ErrosDetail {

    private Date timestamp;
    private String mensagem;
    private String detail;

    public ErrosDetail(Date timestamp, String mensagem, String detail) {
        this.timestamp = timestamp;
        this.mensagem = mensagem;
        this.detail = detail;
    }
}
