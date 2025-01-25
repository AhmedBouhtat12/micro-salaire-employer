package com.example.userrservice.Configg;

import com.example.userrservice.entities.Absence;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@FeignClient(name = "SERVICE-ABSENCES", url = "http://localhost:9099/api/absences")
public interface AbsenceRestClient {

    @GetMapping("/{id}")
    Absence getAbsenceById(@PathVariable("id") Long id);

    @GetMapping
    List<Absence> getAllAbsence();

}