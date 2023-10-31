package kr.jc.chapter5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Chapter5Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter5Application.class, args);
	}

}
