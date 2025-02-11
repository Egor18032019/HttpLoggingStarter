package t1.example.task4;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.stream.Collectors;

public class HttpLoggingInterceptor implements HandlerInterceptor {
    private final HttpLoggingProperties properties;

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingInterceptor.class);
    private StopWatch stopWatch;

    public HttpLoggingInterceptor(HttpLoggingProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        this.stopWatch = new StopWatch();
        this.stopWatch.start(request.getRequestURI());

        logger.info("Метод запроса: " + request.getMethod());
        logger.info("URL: " + request.getRequestURI());
        logger.info("Заголовки запроса: " + getHeadersAsString(request));
        return true;
    }

    @Override
    //вызывается после завершения полного запроса и создания представления.
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        this.stopWatch.stop();
        logger.info("Время обработки запроса: " + this.stopWatch.prettyPrint());

    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) {
        System.out.println("вызывается после выполнения обработчика - то есть ответ сервера");
        logger.info("Заголовки ответа: " + getHeadersAsString(response));
        logger.info("Код ответа: " + response.getStatus());

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

}
