package service.emp.serviceemployer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableEurekaServer
@EnableFeignClients
@EnableDiscoveryClient
public class ServiceEmployerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceEmployerApplication.class, args);
    }

}
