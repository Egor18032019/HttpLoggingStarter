package t1.example.task4.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogHTTPAspect {

    @Around("@annotation(t1.example.task4.aop.LogHTTP)")
    public Object loggingHTTPRequest(ProceedingJoinPoint joinPoint) {
        log.info("Aspect is triggered for method: {}", joinPoint.getSignature().getName());
        Object result = null;
        try {
            result = joinPoint.proceed();//Important

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


        return result;
    }


}
