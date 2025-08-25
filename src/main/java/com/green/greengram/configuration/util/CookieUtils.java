package com.green.greengram.configuration.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//쿠키에 데이터 담고 빼고 할 때 사용하는 객체
@Slf4j
@Component //빈등록
public class CookieUtils {
    /*
    response: 쿠키를 담을 때 필요함
    name: 쿠키에 담을 벨류의 레이블(키값)
    value: 쿠키에 담을 벨류
    maxAge: 쿠키에 담긴 벨류의 유효 기간
    path: 설정한 경로에 요청이 갈 때만 쿠키가 전달된다.
     */
    public void setCookie(HttpServletResponse response, String name, String value, int maxAge, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true); //보안 쿠키 설정
        response.addCookie(cookie);
//        ResponseCookie cookie = ResponseCookie.from(name, value)
//                .path(path)
//                //.sameSite("None") //secure가 true일때 동작한다.
//                .httpOnly(true)
//                .secure(false) //https일 때만 쿠키 전송된다.
//                .maxAge(maxAge)
//                .build();
//
//        response.addHeader("Set-Cookie", cookie.toString());
    }

    public String getValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        if(cookie == null) { return null; }
        return cookie.getValue();
    }

    private Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies(); //쿠키가 req에 여러개가 있을 수 있기 때문에 배열로 리턴

        if (cookies != null && cookies.length > 0) { //쿠키에 뭔가 담겨져 있다면
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) { //쿠키에 담긴 이름이 같은게 있다면
                    return cookie; //해당 쿠키를 리턴
                }
            }
        }
        return null;
    }

    public void deleteCookie(HttpServletResponse response, String name, String path) {
        setCookie(response, name, null, 0, path);
    }
}
