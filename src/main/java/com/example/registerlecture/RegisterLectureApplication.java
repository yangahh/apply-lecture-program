package com.example.registerlecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class RegisterLectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterLectureApplication.class, args);
    }

}
