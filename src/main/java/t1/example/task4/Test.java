package t1.example.task4;

import org.springframework.stereotype.Component;
import t1.example.task4.aop.LogHTTP;

@Component
@LogHTTP
public class Test {
    public void test() {
        System.out.println("Test");
        System.out.println("                    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1              Test");

    }
}
