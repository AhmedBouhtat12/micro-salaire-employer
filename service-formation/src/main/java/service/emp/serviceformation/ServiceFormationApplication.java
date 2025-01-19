package service.emp.serviceformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaServer
@EnableFeignClients
public class ServiceFormationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceFormationApplication.class, args);
    }

}
