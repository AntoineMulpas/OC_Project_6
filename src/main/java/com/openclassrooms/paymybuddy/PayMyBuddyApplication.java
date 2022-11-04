package com.openclassrooms.paymybuddy;

import com.openclassrooms.paymybuddy.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PayMyBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

        };
    }

}
