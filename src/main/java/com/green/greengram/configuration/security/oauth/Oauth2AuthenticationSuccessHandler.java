package com.green.greengram.configuration.security.oauth;

import com.green.greengram.configuration.CookieUtils;
import com.green.greengram.configuration.constants.ConstOAuth2;
import com.green.greengram.configuration.jwt.JwtUser;
import com.green.greengram.configuration.jwt.TokenProvider;
import com.green.greengram.configuration.security.MyUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final Oauth2AuthenticationRequestBasedOnCookieRepository repository;
    private final TokenProvider tokenProvider;
    private final ConstOAuth2 constOAuth2;
    private final CookieUtils cookieUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
        throws IOException, ServletException {
        if(res.isCommitted()) { //응답 객체가 만료된 경우 (이전 프로세스에서 응답처리를 했는 상태)
            log.error("onAuthenticationSuccess called with a committed response {}", res);
            return;
        }
        String targetUrl = this.determineTargetUrl(req, res, auth);
        log.info("onAuthenticationSuccess targetUrl={}", targetUrl);
        clearAuthenticationAttributes(req, res);
        getRedirectStrategy().sendRedirect(req, res, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest req, HttpServletResponse res, Authentication auth) {
        String redirectUrl = cookieUtils.getValue(req, constOAuth2.getRedirectUriParamCookieName(), String.class);

        log.info("determineTargetUrl > getDefaultTargetUrl(): {}", getDefaultTargetUrl());

        String targetUrl = redirectUrl == null ? getDefaultTargetUrl() : redirectUrl;

        //쿼리스트링 생성을 위한 준비과정
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        OAuth2JwtUser oAuth2JwtUser = (OAuth2JwtUser)myUserDetails.getJwtUser();

        JwtUser jwtUser = new JwtUser(oAuth2JwtUser.getSignedUserId(), oAuth2JwtUser.getRoles());

        //AT, RT 생성
        String accessToken = tokenProvider.generateAccessToken(jwtUser);
        String refreshToken = tokenProvider.generateRefreshToken(jwtUser);

        int maxAge = 1_296_000; //15 * 24 * 60 * 60, 15일의 초(second)값
        cookieUtils.setCookie(res, "refreshToken", refreshToken, maxAge, "/api/user/access-token");

        /*
            쿼리스트링 생성
            targetUrl: /fe/redirect
            accessToken: aaa
            userId: 20
            nickName: 홍길동
            pic: abc.jpg
            값이 있다고 가정하고
            "fe/redirect?access_token=aaa&user_id=20&nick_name=홍길동&pic=abc.jpg"
         */
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("access_token", accessToken)
                .queryParam("user_id", oAuth2JwtUser.getSignedUserId())
                .queryParam("nick_name", oAuth2JwtUser.getNickName()).encode()
                .queryParam("pic", oAuth2JwtUser.getPic())
                .build()
                .toUriString();
    }

    private void clearAuthenticationAttributes(HttpServletRequest req, HttpServletResponse res) {
        super.clearAuthenticationAttributes(req);
        repository.removeAuthorizationCookies(res);
    }
}
