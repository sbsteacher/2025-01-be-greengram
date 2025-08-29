package com.green.greengram.application.pay;



import com.green.greengram.application.pay.model.KakaoPayApproveFeignReq;
import com.green.greengram.application.pay.model.KakaoPayApproveRes;
import com.green.greengram.application.pay.model.KakaoPayReadyFeignReq;
import com.green.greengram.application.pay.model.KakaoPayReadyRes;
import com.green.greengram.configuration.feignclient.KakaoPayClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kakaoPayApi"
           , url = "${constants.pay.kakao.base-url}"
           , configuration = { KakaoPayClientConfiguration.class })
public interface KakaoPayFeignClient {

    @PostMapping(value = "/ready")
    KakaoPayReadyRes postReady(@RequestBody KakaoPayReadyFeignReq req);

    @PostMapping(value = "/approve")
    KakaoPayApproveRes postApprove(KakaoPayApproveFeignReq req);
}
