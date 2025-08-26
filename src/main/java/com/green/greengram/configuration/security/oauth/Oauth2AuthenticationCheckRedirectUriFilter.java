package com.green.greengram.configuration.security.oauth;

import com.green.greengram.configuration.constants.ConstOAuth2;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class Oauth2AuthenticationCheckRedirectUriFilter extends OncePerRequestFilter {

    private final ConstOAuth2 constOAuth2;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
            호스트 주소값을 제외한 요청한 URI
            예) http://localhost:8080/oauth2/authorization?redirect_uri=abc
            호스트 주소값: http://localhost:8080
            제외한 요청한 URI(requestUri): /oauth2/authorization?redirect_uri=abc
            String redirectUri = abc;
         */
        String requestUri = request.getRequestURI();
        log.info("request uri: {}", requestUri);
        if(requestUri.startsWith(constOAuth2.baseUri)) { //소셜로그인 요청한 것이라면
            String redirectUri = request.getParameter("redirect_uri");
            if(redirectUri != null && !hasAuthorizedRedirectUri(redirectUri)) { //약속한 redirect_uri값이 아니었다면
                String errRedirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                                                            .queryParam("error", "유효한 Redirect URL이 아닙니다.").encode()
                                                            .toUriString();
                //errRedirectUrl = "/fe/direct?error=유효한 Redirect URL이 아닙니다."
                response.sendRedirect(errRedirectUrl);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    //약속한 redirect_uri가 맞는지 체크 없으면 false, 있으면 true 리턴
    private boolean hasAuthorizedRedirectUri(String redirectUri) {
        for(String uri : constOAuth2.authorizedRedirectUris) {
            if(uri.equals(redirectUri)) {
                return true;
            }
        }
        return false;
    }
}
