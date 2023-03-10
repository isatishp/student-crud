package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Random;

import static java.util.Optional.ofNullable;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        var name = getName();
        System.out.println(name);
        return () -> ofNullable(name);

    }

    private String getName() {
        var random = new Random().nextInt(10);
        System.out.println(random);
        return (random % 2) == 1 ? "SATISH" : null;
    }
}
