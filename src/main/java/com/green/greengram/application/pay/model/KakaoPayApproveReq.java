package com.green.greengram.application.pay.model;

import lombok.Getter;
import org.springframework.web.bind.annotation.BindParam;

@Getter
public class KakaoPayApproveReq {
    private final String pgToken;

    public KakaoPayApproveReq(@BindParam("pg_token") String pgToken) {
        this.pgToken = pgToken;
    }
}
