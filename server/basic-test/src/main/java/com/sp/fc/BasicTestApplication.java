package com.sp.fc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BasicTestApplication {

    public static void main(String[] args) {

        Person person = null;

        SpringApplication.run(BasicTestApplication.class, args);
    }
}
