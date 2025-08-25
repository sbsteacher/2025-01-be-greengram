package com.green.greengram.configuration.util;

import jakarta.servlet.http.HttpServletRequest;

public class HttpUtils {

    //세션에 데이터 저장
    public static void setSession(HttpServletRequest req, String key, Object value) {
        req.getSession().setAttribute(key, value);
    }

    //세션에 저장된 데이터 얻기
    public static Object getSessionValue(HttpServletRequest req, String key) {
        return req.getSession().getAttribute(key);
    }

    //세션에 저장된 데이터 삭제
    public static void removeSessionValue(HttpServletRequest req, String key) {
        req.getSession().removeAttribute(key);
    }
}
