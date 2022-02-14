package com.rapdeploy.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(
        scanBasePackages = {
                "com.rapdeploy.customer",
                "com.rapdeploy.amqp",
        }
)
@EnableEurekaClient
//@EnableFeignClients(
//        basePackages = "com.rapdeploy.clients"
//)
//@PropertySources({
//        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
//})
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
