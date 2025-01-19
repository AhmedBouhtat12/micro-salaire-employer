package service.emp.serviceemployer.Config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SERVICE-DEPARTEMENT", url = "http://localhost:9097/api/departements")
public interface DepartementFeignClient {

    @GetMapping("/{id}")
    Object getDepartementById(@PathVariable Long id);
}