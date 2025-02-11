package t1.example.task4;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableConfigurationProperties(HttpLoggingProperties.class)
@ConditionalOnProperty(name = "logging.http.enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
@ConditionalOnClass(WebMvcConfigurer.class)
public class HttpLoggingAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public HttpLoggingInterceptor httpLoggingAspect() {
        HttpLoggingProperties properties = new HttpLoggingProperties();
        return new HttpLoggingInterceptor(properties);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpLoggingAspect());
    }
}