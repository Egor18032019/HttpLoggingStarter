package t1.example.task4;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//@Configuration
//@EnableConfigurationProperties(HttpLoggingProperties.class)
//@ConditionalOnProperty(name = "logging.http.enabled", havingValue = "true", matchIfMissing = true)
//@ConditionalOnWebApplication
//@ConditionalOnClass(WebMvcConfigurer.class)
public class HttpLoggingAutoConfiguration implements WebMvcConfigurer {

    HttpLoggingProperties properties;

    public HttpLoggingAutoConfiguration(HttpLoggingProperties properties) {
        this.properties = properties;
    }

    @Bean
    public HttpLoggingInterceptor httpLoggingAspect() {

        return new HttpLoggingInterceptor(properties);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpLoggingAspect());
    }
}