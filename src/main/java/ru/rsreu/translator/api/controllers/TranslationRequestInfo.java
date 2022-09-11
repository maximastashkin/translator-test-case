package ru.rsreu.translator.api.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TranslationRequestInfo {
    @Getter
    private final LocalDateTime requestTime;
    @Getter
    private final String ipAddress;

    @Autowired
    public TranslationRequestInfo(HttpServletRequest httpServletRequest) {
        requestTime = LocalDateTime.now();
        ipAddress = httpServletRequest.getRemoteAddr();
    }
}
