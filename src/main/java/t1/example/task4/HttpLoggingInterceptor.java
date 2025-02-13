package t1.example.task4;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
public class HttpLoggingInterceptor implements HandlerInterceptor {
    private final HttpLoggingProperties properties;

    private StopWatch stopWatch;

    public HttpLoggingInterceptor(HttpLoggingProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        this.stopWatch = new StopWatch();
        this.stopWatch.start(request.getRequestURI());
        logRequest(request);
        return true;
    }

    @Override
    //вызывается после завершения полного запроса и создания представления.
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        this.stopWatch.stop();
        log.info("Время обработки запроса: " + this.stopWatch.prettyPrint());

    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logResponse(response);
    }

    private String getHeadersAsString(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .map(headerName -> headerName + "=" + request.getHeader(headerName))
                .collect(Collectors.joining(", "));
    }

    private String getHeadersAsString(HttpServletResponse response) {
        return response.getHeaderNames()
                .stream()
                .map(headerName -> headerName + "=" + response.getHeader(headerName))
                .collect(Collectors.joining(", "));
    }

    private void logRequest(HttpServletRequest request) {
        switch (properties.getLevel().toUpperCase()) {
            case "DEBUG":
                log.debug("Метод запроса: " + request.getMethod());
                log.debug("URL: " + request.getRequestURI());
                log.debug("Заголовки запроса: " + getHeadersAsString(request));
                break;
            case "INFO":
                log.info("Метод запроса: " + request.getMethod());
                log.info("URL: " + request.getRequestURI());
                log.info("Заголовки запроса: " + getHeadersAsString(request));
                break;
            case "WARN":
                log.warn("Метод запроса: " + request.getMethod());
                log.warn("URL: " + request.getRequestURI());
                log.warn("Заголовки запроса: " + getHeadersAsString(request));
                break;
            case "ERROR":
                log.error("Метод запроса: " + request.getMethod());
                log.error("URL: " + request.getRequestURI());
                log.error("Заголовки запроса: " + getHeadersAsString(request));
                break;
            default:
                log.info("Request to {}  ", Arrays.toString(request.getCookies()));
        }
    }

    private void logResponse(HttpServletResponse response) {
        switch (properties.getLevel().toUpperCase()) {
            case "DEBUG":
                log.debug("Заголовки ответа: " + getHeadersAsString(response));
                log.debug("Код ответа: " + response.getStatus());
                break;
            case "INFO":
                log.info("Заголовки ответа: " + getHeadersAsString(response));
                log.info("Код ответа: " + response.getStatus());
                break;
            case "WARN":
                log.warn("Заголовки ответа: " + getHeadersAsString(response));
                log.warn("Код ответа: " + response.getStatus());
                break;
            case "ERROR":
                log.error("Заголовки ответа: " + getHeadersAsString(response));
                log.error("Код ответа: " + response.getStatus());
                break;
            default:
                log.info("Response to {}  ", Arrays.toString(response.getHeaderNames().toArray()));
        }
    }
}
