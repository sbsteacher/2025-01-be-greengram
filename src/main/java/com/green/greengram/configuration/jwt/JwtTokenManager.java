package com.green.greengram.configuration.jwt;


import com.green.greengram.configuration.constants.ConstJwt;
import com.green.greengram.configuration.model.JwtUser;
import com.green.greengram.configuration.model.UserPrincipal;
import com.green.greengram.configuration.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

//JWT 총괄 책임자
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenManager {
    private final ConstJwt constJwt; //설정 내용(문자열)
    private final CookieUtils cookieUtils; //쿠키 관련
    private final JwtTokenProvider jwtTokenProvider; //JWT 관련

    public void issue(HttpServletResponse response, JwtUser jwtUser) {
        setAccessTokenInCookie(response, jwtUser);
        setRefreshTokenInCookie(response, jwtUser);
    }

    public String generateAccessToken(JwtUser jwtUser) {
        return jwtTokenProvider.generateToken(jwtUser, constJwt.getAccessTokenValidityMilliseconds());
    }

    public void setAccessTokenInCookie(HttpServletResponse response, JwtUser jwtUser) {
        setAccessTokenInCookie(response, generateAccessToken(jwtUser));
    }

    public void setAccessTokenInCookie(HttpServletResponse response, String accessToken) {
        cookieUtils.setCookie(response
                            , constJwt.getAccessTokenCookieName()
                            , accessToken
                            , constJwt.getAccessTokenCookieValiditySeconds()
                            , constJwt.getAccessTokenCookiePath());
    }

    public String getAccessTokenFromCookie(HttpServletRequest request) {
        return cookieUtils.getValue(request, constJwt.getAccessTokenCookieName());
    }

    public void deleteAccessTokenInCookie(HttpServletResponse response) {
        cookieUtils.deleteCookie(response, constJwt.getAccessTokenCookieName(), constJwt.getAccessTokenCookiePath());
    }

    public String generateRefreshToken(JwtUser jwtUser) {
        return jwtTokenProvider.generateToken(jwtUser, constJwt.getRefreshTokenValidityMilliseconds());
    }

    public void setRefreshTokenInCookie(HttpServletResponse response, JwtUser jwtUser) {
        setRefreshTokenInCookie(response, generateRefreshToken(jwtUser));
    }

    public void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
        cookieUtils.setCookie(response, constJwt.getRefreshTokenCookieName(), refreshToken, constJwt.getRefreshTokenCookieValiditySeconds(), constJwt.getRefreshTokenCookiePath());
    }

    public void deleteRefreshTokenInCookie(HttpServletResponse response) {
        cookieUtils.deleteCookie(response, constJwt.getRefreshTokenCookieName(), constJwt.getRefreshTokenCookiePath());
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        return cookieUtils.getValue(request, constJwt.getRefreshTokenCookieName());
    }

    public JwtUser getJwtUserFromToken(String token) {
        return jwtTokenProvider.getJwtUserFromToken(token);
    }

    private void deleteSocialLogin(HttpServletResponse response) {
        cookieUtils.deleteCookie(response, "JSESSIONID", null);
        cookieUtils.deleteCookie(response, "Authorization", null);
        cookieUtils.deleteCookie(response, "RefreshToken", null);
    }

    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        //request에서 refreshToken을 얻는다.
        String refreshToken = getRefreshTokenFromCookie(request);

        //refreshToken에서 jwtUser를 만든다.
        JwtUser jwtUser = getJwtUserFromToken(refreshToken);

        //jwtUser로 accessToken을 발행한다.
        String accessToken = generateAccessToken(jwtUser);

        //accessToken을 쿠키에 담는다.
        setAccessTokenInCookie(response, accessToken);
    }

    public void signOut(HttpServletResponse response) {
        deleteAccessTokenInCookie(response);
        deleteRefreshTokenInCookie(response);
        deleteSocialLogin(response);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String accessToken = getAccessTokenFromCookie(request);
        if(accessToken == null){ return null; }
        JwtUser jwtUser = getJwtUserFromToken(accessToken);
        //if(jwtUser == null) { return null; } //토큰이 오염됐을 시 예외발생하기 때문에 null처리는 안 해도 된다.
        UserPrincipal userPrincipal = new UserPrincipal(jwtUser);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }
}
