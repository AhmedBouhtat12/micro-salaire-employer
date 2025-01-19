package service.emp.servicedepartement.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "SERVICE-EMPLOYER", url = "http://localhost:9090/api/employes")
public interface EmployeFeignClient {

    @GetMapping("/{id}")
    Object getEmployeById(@PathVariable Long id);

    @GetMapping
    List<Object> getAllEmployes();
}