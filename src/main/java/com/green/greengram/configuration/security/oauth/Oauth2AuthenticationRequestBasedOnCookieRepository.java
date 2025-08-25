package com.green.greengram.configuration.security.oauth;

import com.green.greengram.configuration.util.CookieUtils;
import com.green.greengram.configuration.constants.ConstOAuth2;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationRequestBasedOnCookieRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final CookieUtils cookieUtils;
    private final ConstOAuth2 constOAuth2;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return cookieUtils.getValue(request
                                  , constOAuth2.authorizationRequestCookieName
                                  , OAuth2AuthorizationRequest.class);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if(authorizationRequest == null) {
            this.removeAuthorizationCookies(response);
        }
        cookieUtils.setCookie(response
                            , constOAuth2.authorizationRequestCookieName
                            , authorizationRequest
                            , constOAuth2.cookieExpirySeconds
                            , "/");

        //FE 요청한 redirect_uri 쿠키에 저장한다.
        String redirectUriAfterLogin = request.getParameter(constOAuth2.redirectUriParamCookieName);
        cookieUtils.setCookie(response
                , constOAuth2.redirectUriParamCookieName
                , redirectUriAfterLogin
                , constOAuth2.cookieExpirySeconds
                , "/");
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationCookies(HttpServletResponse response) {
        cookieUtils.deleteCookie(response, constOAuth2.authorizationRequestCookieName, "/");
        cookieUtils.deleteCookie(response, constOAuth2.redirectUriParamCookieName, "/");
    }
}
