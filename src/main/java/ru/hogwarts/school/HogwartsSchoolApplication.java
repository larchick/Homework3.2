package ru.hogwarts.school;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class HogwartsSchoolApplication {

    public final static Logger logger = LoggerFactory.getLogger(HogwartsSchoolApplication.class);

    public static void main(String[] args) {
        logger.info("Application is starting! ... ");
        SpringApplication.run(HogwartsSchoolApplication.class, args);
    }

}
