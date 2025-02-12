package t1.example.task4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import t1.example.task4.aop.LogHTTP;

@RestController

public class MyController {

    @GetMapping("/test")
    public String test() {
        return "Hello, World!";
    }
}