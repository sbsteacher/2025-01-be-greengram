package com.green.greengram.application.pay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoPayApproveAmountRes {
    private int total;          //전체 결제 금액
    @JsonProperty("tax_free")
    private int taxFree;        //비과세 금액
    private int vat;            //부가세 금액
    private int point;          //사용한 포인트 금액
    private int discount;       //할인 금액
}
