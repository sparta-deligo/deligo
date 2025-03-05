package com.example.deligo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DeligoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeligoApplication.class, args);
    }

}