package br.com.gamestore;

import org.springframework.boot.SpringApplication; // 2. Adicione este import
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameStoreApplication {
    public static void main(String[] args) {
        // Agora o Java sabe quem é o SpringApplication
        SpringApplication.run(GameStoreApplication.class, args);
    }
}
