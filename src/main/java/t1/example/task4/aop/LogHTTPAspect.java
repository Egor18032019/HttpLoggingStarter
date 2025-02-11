package t1.example.task4.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(0)
public class LogHTTPAspect {

    @Value("${http.log.level}")
    private String level;

    @Around("@annotation(t1.example.task4.aop.LogHTTP)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) {
        System.out.println("!!! MetricAspect.measureExecutionTime  !!!");

        Object result = null;
        try {
            result = joinPoint.proceed();//Important

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


        return result;
    }

}
