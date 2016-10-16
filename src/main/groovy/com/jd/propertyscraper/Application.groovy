package com.jd.propertyscraper

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication(scanBasePackages = ['com.jd'])
public class Application{

    public static void main(String[] args) {
        SpringApplication.run Application, args
    }
    
    @Bean
    public RestTemplate restTemplate(){

        def restTemplate = new RestTemplate()
        restTemplate
    }


}