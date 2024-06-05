package com.vasche.shoestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
// Аннотация @EnableTransactionManagement включает поддержку транзакций в приложении Spring Boot.
// Это позволяет использовать транзакции в сервисах и контроллерах.
// Spring Boot использует менеджер транзакций (PlatformTransactionManager), который отвечает за управление транзакциями.
public class ShoestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoestoreApplication.class, args);
	}

}
