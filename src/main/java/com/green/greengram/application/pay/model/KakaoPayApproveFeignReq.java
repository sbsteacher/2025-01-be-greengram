package com.green.greengram.application.pay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoPayApproveFeignReq {
    private String cid; //가맹점 코드, 10자
    private String tid; //결제 고유번호, 결제 준비 API 응답에 포함

    @JsonProperty("partner_order_id")
    private String partnerOrderId; //가맹점 주문번호, 결제 준비 API 요청과 일치해야 함

    @JsonProperty("partner_user_id")
    private String partnerUserId; //가맹점 회원 id, 결제 준비 API 요청과 일치해야 함

    @JsonProperty("pg_token")
    private String pgToken; //결제승인 요청을 인증하는 토큰 사용자 결제 수단 선택 완료 시, approval_url로 redirection 해줄 때 pg_token을 query string으로 전달

    private String payload; //결제 승인 요청에 대해 저장하고 싶은 값, 최대 200자
}
