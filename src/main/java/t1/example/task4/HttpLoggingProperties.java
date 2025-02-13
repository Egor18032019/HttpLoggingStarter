package t1.example.task4;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HttpLoggingProperties {

    private String level;

    public HttpLoggingProperties(String level) {
        this.level = level;
    }
}