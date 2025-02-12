package t1.example.task4;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import t1.example.task4.aop.LogHTTP;

@Component
@RequiredArgsConstructor
public class CustomStartupAnnotationProcessor implements BeanPostProcessor {

    @Value("${logging.http.enabled}")
    private Boolean loggingHttpEnabled;
    @Value("${logging.http.level}")
    private String level;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(LogHTTP.class)) {
            System.out.println("CustomStartupAnnotation detected on class: " + bean.getClass().getSimpleName());
            if (loggingHttpEnabled) {
                HttpLoggingProperties properties=new HttpLoggingProperties(level);

                HttpLoggingAutoConfiguration httpLoggingAutoConfiguration = new HttpLoggingAutoConfiguration(properties);
                return BeanPostProcessor.super.postProcessBeforeInitialization(httpLoggingAutoConfiguration, beanName);
            }


        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}