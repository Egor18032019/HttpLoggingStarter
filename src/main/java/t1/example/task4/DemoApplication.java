package t1.example.task4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import t1.example.task4.aop.LogHTTP;

@SpringBootApplication
@LogHTTP
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		Test t = new Test();
		t.test();
	}

}
