package t1.example.task4;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import t1.example.task4.aop.LogHTTP;

@Component
@RequiredArgsConstructor
public class CustomStartupAnnotationProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;
    @Value("${logging.http.enabled}")
    private Boolean loggingHttpEnabled;
    @Value("${logging.http.level}")
    private String level;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(LogHTTP.class)) {
            System.out.println("CustomStartupAnnotation detected on class: " + bean.getClass().getSimpleName());
            if (loggingHttpEnabled) {
                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
                boolean isGreatHttpLoggingAutoConfiguration = beanFactory.containsBean("httpLoggingAutoConfiguration");
                if (!isGreatHttpLoggingAutoConfiguration) {
                    HttpLoggingProperties properties = new HttpLoggingProperties(level);
                    HttpLoggingAutoConfiguration httpLoggingAutoConfiguration = new HttpLoggingAutoConfiguration(properties);
                    // Регистрируем бин в контексте
                    beanFactory.registerSingleton("httpLoggingAutoConfiguration", httpLoggingAutoConfiguration);
//                    postProcessBeforeInitialization(httpLoggingAutoConfiguration, "httpLoggingAutoConfiguration");
                }

            }


        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}