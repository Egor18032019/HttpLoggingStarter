package t1.example.task4.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Aspect
@Component
public class LogHTTPAspect {
    @Value("${logging.http.enabled}")
    private Boolean loggingHttpEnabled;
    @Value("${logging.http.level}")
    private String level;

    //    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    @Around("@within(org.springframework.stereotype.Controller) || " +
            "@within(org.springframework.web.bind.annotation.RestController)")
    public Object loggingHTTPRequest(ProceedingJoinPoint joinPoint) {
        log.info("Aspect is triggered for method: {}", joinPoint.getSignature().getName());
        Object result = null;
        try {
            if (loggingHttpEnabled) {
                logRequest();
            }
            result = joinPoint.proceed();//Important
            if (loggingHttpEnabled) {
                logResponse();
            }
        } catch (
                Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    private void logRequest() {


        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String method = request.getMethod();
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder stringBuilder = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            stringBuilder.append(headerName).append(": ").append(headerValue).append("\n");
        }
        switch (level) {
            case "DEBUG":
                log.debug("Метод запроса: " + method);
                log.debug("Аргументы запроса: " + request.getParameterMap().toString());
                log.debug("URL: " + request.getRequestURI());

                log.debug("Заголовки запроса: " + stringBuilder);
                break;
            case "INFO":
                log.info("Метод запроса: " + method);
                log.info("Аргументы запроса: " + request.getParameterMap().toString());
                log.info("URL: " + request.getRequestURI());

                log.info("Заголовки запроса: " + stringBuilder);
                break;
            case "WARN":
                log.warn("Метод запроса: " + method);
                log.warn("Аргументы запроса: " + request.getParameterMap().toString());
                log.warn("URL: " + request.getRequestURI());

                log.warn("Заголовки запроса: " + stringBuilder);
                break;
            case "ERROR":
                log.error("Метод запроса: " + method);
                log.error("Аргументы запроса: " + request.getParameterMap().toString());
                log.error("URL: " + request.getRequestURI());

                log.error("Заголовки запроса: " + stringBuilder);
                break;
            default:
                log.info("Request to {}  ", request);
        }
    }


    private void logResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletResponse response = attributes.getResponse();
        String headers = response.getHeaderNames().stream()
                .map(headerName -> headerName + "=" + response.getHeader(headerName))
                .collect(Collectors.joining(", "));
        ; //Возвращает коллекцию имен заголовков
        switch (level) {
            case "DEBUG":
                log.debug("Заголовки ответа: " + headers);
                log.debug("Код ответа: " + response.getStatus());
                break;
            case "INFO":
                log.info("Заголовки ответа: " + headers);
                log.info("Код ответа: " + response.getStatus());
                break;
            case "WARN":
                log.warn("Заголовки ответа: " + headers);
                log.warn("Код ответа: " + response.getStatus());
                break;
            case "ERROR":
                log.error("Заголовки ответа: " + headers);
                log.error("Код ответа: " + response.getStatus());
                break;
            default:
                log.info("Response to {}  ", Arrays.toString(response.getHeaderNames().toArray()));
        }
    }
}




