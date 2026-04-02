package hello.hello_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication { // 톰켓이라는 웹 서버를 내장

	public static void main(String[] args) {

		SpringApplication.run(HelloSpringApplication.class, args);
	}

}
