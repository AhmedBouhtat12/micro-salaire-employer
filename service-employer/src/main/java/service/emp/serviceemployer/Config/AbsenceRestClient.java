package service.emp.serviceemployer.Config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import service.emp.serviceemployer.Entities.Absence;


import java.util.List;

    @FeignClient(name = "SERVICE-ABSENCES", url = "http://localhost:9099/api/absences")
    public interface AbsenceRestClient {

        @GetMapping("/{id}")
        Absence getAbsenceById(@PathVariable("id") Long id);

        @GetMapping
        List<Absence> getAllAbsence();

   }


