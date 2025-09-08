package com.green.greengram.configuration.security;

import com.green.greengram.configuration.jwt.JwtTokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenManager jwtTokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("request.getRequestURI(): {}", request.getRequestURI());
        //토큰 처리
        Authentication authentication = jwtTokenManager.getAuthentication(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication); //인증 처리
        }
        filterChain.doFilter(request, response); //다음 필터에게 req, res 넘기기
    }
}

/*
쿠키  >  세션  > JWT

<로그인 과정>
com.green.greengram.application.user.UserController
(POST) /api/user/sign-in

1. 아이디/비번 일치
2. 토큰에 담을 데이터A-JwtUser(유저pk, Roles)
 , 응답 데이터B-UserSignInRes(유저pk, 사진명, 닉네임) 준비
3. AT/RT 발행(데이터A 포함)
4. AT/RT 보안 쿠키에 담는다.
5. 데이터B를 응답 처리

<요청마다 - 인증 과정>
1. 요청 Head에 AT이 포함되어 있는지 확인
(포함되어 있지 않다면)
2. 인증처리 X

(포함되어 있다면)
2. AT을 Head에서 뽑아낸다.
3. AT를 역직렬화로 데이터A를 뽑아낸다.
4. 데이터A로 Spring Security(SS)에 맞게 인증처리를 한다.
5. SS는 요청URL과 인증 여부/인가에 맞는지 확인하고 문제가 없으면 계속 처리를 하고 문제가 있다면 에러를 터트린다.


-----------------------
AT - 인증/인가 처리 용도 (약 15분)
RT - AT 재발행 용도 (약 15일)
 */