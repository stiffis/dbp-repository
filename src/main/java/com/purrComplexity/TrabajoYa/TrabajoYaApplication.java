package com.purrComplexity.TrabajoYa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TrabajoYaApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrabajoYaApplication.class, args);
    }
}
