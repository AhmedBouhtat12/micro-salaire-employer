package service.emp.serviceemployer.Config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import service.emp.serviceemployer.Entities.FormationDto;

import java.util.List;

@FeignClient(name = "SERVICE-FORMATION", url = "http://localhost:9095/api/formations")
public interface FormationFeignClient {

    @GetMapping("/{id}")
    FormationDto getFormationById(@PathVariable Long id);



}
