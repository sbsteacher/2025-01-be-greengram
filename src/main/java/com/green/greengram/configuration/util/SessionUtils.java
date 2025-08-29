package com.green.greengram.configuration.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@Component
public class SessionUtils {
    private RequestAttributes getRequestAttributes() {
        return Objects.requireNonNull(RequestContextHolder.getRequestAttributes(), "세션이 존재하지 않습니다.");
    }

    public void setAttribute(String name, Object value) {
        getRequestAttributes().setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }

    public String getAttribute(String name) { return getAttribute(name, String.class); }

    public <T> T getAttribute(String name, Class<T> type) {
        return type.cast(getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION));
    }
}
