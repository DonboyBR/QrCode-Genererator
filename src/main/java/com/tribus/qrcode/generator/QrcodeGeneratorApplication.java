package com.tribus.qrcode.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// essa é a classe principal
// já prepara tudo pra sua aplicação web rodar suave.
@SpringBootApplication
public class QrcodeGeneratorApplication {

	// Esse é o método 'main', o ponto de entrada da sua aplicação Java.
	public static void main(String[] args) {
		// Essa linha é o que inicializa o Spring Boot e sobe a aplicação.
		SpringApplication.run(QrcodeGeneratorApplication.class, args);
	}
}