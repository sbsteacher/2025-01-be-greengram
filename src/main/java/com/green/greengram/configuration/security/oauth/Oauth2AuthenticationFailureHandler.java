package com.green.greengram.configuration.security.oauth;


import com.green.greengram.configuration.constants.ConstOAuth2;
import com.green.greengram.configuration.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final Oauth2AuthenticationRequestBasedOnCookieRepository repository;
    private final CookieUtils cookieUtils;
    private final ConstOAuth2 constOAuth2;

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException exception)
    throws IOException{
        exception.printStackTrace();

        //FE - Redirect-Url 획득 from Cookie
        String redirectUrl = cookieUtils.getValue(req, constOAuth2.redirectUriParamCookieName);

        log.info("Oauth2AuthenticationFailureHandler - onAuthenticationFailure(): {}", redirectUrl);

        //URL에 에러 쿼리스트링 추가
        String targetUrl = redirectUrl == null ? "/" : UriComponentsBuilder.fromUriString(redirectUrl)
                                                                           .queryParam("error", exception.getLocalizedMessage())
                                                                           .build()
                                                                           .toUriString();
        //targetUrl = "http://프론트 호스트 주소값/fe/redirect?error=에러메세지";
        getRedirectStrategy().sendRedirect(req, res, targetUrl);

    }

}
