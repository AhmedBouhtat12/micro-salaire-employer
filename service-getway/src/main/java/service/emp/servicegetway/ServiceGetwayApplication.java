package service.emp.servicegetway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;


@SpringBootApplication
@EnableDiscoveryClient
public class ServiceGetwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceGetwayApplication.class, args);
    }
}
