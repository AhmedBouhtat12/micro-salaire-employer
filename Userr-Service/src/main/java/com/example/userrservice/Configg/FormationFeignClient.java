package com.example.userrservice.Configg;

import com.example.userrservice.entities.FormationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "SERVICE-FORMATION", url = "http://localhost:9095/api/formations")
public interface FormationFeignClient {

    @GetMapping("/{id}")
    FormationDto getFormationById(@PathVariable Long id);



}
