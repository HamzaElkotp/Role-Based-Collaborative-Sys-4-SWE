package com.se2project.piplineservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {
        "com.se2project.piplineservice",
        "com.example.modifying_phase_service",
        "com.example.reviewing_phase_service",
        "testing_service.demo"
})
@EnableJpaRepositories(basePackages = {
        "com.se2project.piplineservice.Repository",
        "com.example.modifying_phase_service.repository",
        "com.example.reviewing_phase_service.repository",
        "testing_service.demo.repository"
})
@EntityScan(basePackages = {
        "com.se2project.piplineservice.entity",
        "com.se2project.piplineservice.model",
        "com.example.modifying_phase_service.entity",
        "com.example.reviewing_phase_service.model",
        "testing_service.demo.entity"
})
public class PipelineApplication {
    public static void main(String[] args) {
        SpringApplication.run(PipelineApplication.class, args);
    }
}
