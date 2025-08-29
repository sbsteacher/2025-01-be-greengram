package com.green.greengram.application.pay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoPayReadyFeignReq {
    private String cid;

    @JsonProperty("partner_order_id")
    private String partnerOrderId;

    @JsonProperty("partner_user_id")
    private String partnerUserId;

    @JsonProperty("item_name")
    private String itemName;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("total_amount")
    private Integer totalAmount;

    @JsonProperty("tax_free_amount")
    private Integer taxFreeAmount;

    @JsonProperty("vat_amount")
    private Integer vatAmount;

    @JsonProperty("approval_url") //결제 성공 시 redirect url, 최대 255자
    private String approvalUrl;

    @JsonProperty("cancel_url") //결제 취소 시 redirect url, 최대 255자
    private String cancelUrl;

    @JsonProperty("fail_url") //결제 실패 시 redirect url, 최대 255자
    private String failUrl;

}
