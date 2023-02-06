package com.ironhack.iron_bank_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableScheduling
public class IronBankProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(IronBankProjectApplication.class, args);
    }


}
