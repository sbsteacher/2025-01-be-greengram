package com.green.greengram.application.pay;

import com.green.greengram.application.pay.model.*;
import com.green.greengram.configuration.constants.ConstKakaoPay;
import com.green.greengram.configuration.util.SessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayService {
    private final KakaoPayFeignClient kakaoPayFeignClient;
    private final SessionUtils sessionUtils;
    private final ConstKakaoPay constKakaoPay;

    public KakaoPayReadyRes postReady() {
        KakaoPayReadyFeignReq feignReq = KakaoPayReadyFeignReq.builder()
                .cid(constKakaoPay.cid)
                .partnerOrderId("1")
                .partnerUserId("2") //결제 유저 ID
                .itemName("테스트 외 2개")
                .quantity(5) //수량
                .totalAmount(35_000) //총 결제금액
                .taxFreeAmount(0)
                .approvalUrl(constKakaoPay.approvalUrl) //QR처리를 하면 리다이렉트되는 주소값
                .failUrl(constKakaoPay.failUrl) //결제 실패하면 리다이렉트되는 주소값
                .cancelUrl(constKakaoPay.cancelUrl) //결제 취소하면 리다이렉트되는 주소값
                .build();
        KakaoPayReadyRes res = kakaoPayFeignClient.postReady(feignReq); //결제 준비단계 요청을 보내고 응답으로 tid를 얻을 수 있다.

        log.info("tid: {}", res.getTid());

        //세션에 결제 정보 저장 ( 결제 승인 때 결제 준비 단계에서 보낸 tid, partnerOrderId, partnerUserId가 같아야 한다. 그래서 세션에 저장함 )
        KakaoPaySessionDto dto = KakaoPaySessionDto.builder()
                .tid(res.getTid())
                .partnerOrderId(feignReq.getPartnerOrderId())
                .partnerUserId(feignReq.getPartnerUserId())
                .build();

        sessionUtils.setAttribute(constKakaoPay.kakaoPayInfoSessionName, dto);
        return res;
    }

    public KakaoPayApproveRes getApprove(KakaoPayApproveReq req) {
        //카카오페이 준비과정에서 세션에 저장한 tid, partnerOrderId, partnerUserId 가져오기
        KakaoPaySessionDto dto = sessionUtils.getAttribute(constKakaoPay.kakaoPayInfoSessionName, KakaoPaySessionDto.class);
        log.info("결제승인 요청을 인증하는 토큰: {}", req.getPgToken());
        //log.info("결제 고유번호: {}", tid);

        KakaoPayApproveFeignReq feignReq = KakaoPayApproveFeignReq.builder()
                .tid(dto.getTid())
                .cid(constKakaoPay.cid)
                .partnerOrderId(dto.getPartnerOrderId())
                .partnerUserId(dto.getPartnerUserId())
                .pgToken(req.getPgToken())
                .payload("테스트")
                .build();

        KakaoPayApproveRes res = kakaoPayFeignClient.postApprove(feignReq);
        log.info("res: {}", res);
        return res;
    }
}
