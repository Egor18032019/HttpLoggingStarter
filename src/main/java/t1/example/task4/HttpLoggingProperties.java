package t1.example.task4;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "logging.http")
@Getter
@Setter
public class HttpLoggingProperties {
    private boolean enabled = true;
    private String level = "INFO";


}