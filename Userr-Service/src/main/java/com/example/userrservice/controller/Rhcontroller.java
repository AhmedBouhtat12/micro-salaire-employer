package com.example.userrservice.controller;

import com.example.userrservice.Configg.AbsenceRestClient;
import com.example.userrservice.Configg.DepartementFeignClient;
import com.example.userrservice.Configg.FormationFeignClient;
import com.example.userrservice.entities.Absence;
import com.example.userrservice.entities.FormationDto;
import com.example.userrservice.entities.User;
import com.example.userrservice.repository.UserRepository;
import com.example.userrservice.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/Rh")
//@PreAuthorize("hasRole('EMPLOYER')")
public class Rhcontroller {
    @Autowired
    private EmployerService employerService;
    @Autowired
    private DepartementFeignClient departementFeignClient;
    @Autowired
    private FormationFeignClient formationFeignClient;
    @Autowired
    private AbsenceRestClient absenceRestClient;



    @GetMapping("/{employerId}/absences")
    public ResponseEntity<List<Absence>> getAbsencesByEmployerId(@PathVariable Long employerId) {
        Optional<User> employer = employerService.getEmployerById(employerId);
        if (employer.isPresent()) {
            List<Long> absencesId = employer.get().getAbsencesId();

            List<Absence> absences = absencesId.stream()
                    .map(absenceRestClient::getAbsenceById)
                    .toList();
            return ResponseEntity.ok(absences);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{employerId}/formations")
    public ResponseEntity<List<FormationDto>> getFormationsByEmployerId(@PathVariable Long employerId) {
        Optional<User> employer = employerService.getEmployerById(employerId);
        if (employer.isPresent()) {
            List<Long> formationIds = employer.get().getFormationId();

            List<FormationDto> formations = formationIds.stream()
                    .map(formationId -> {
                        try {
                            FormationDto formation = formationFeignClient.getFormationById(formationId);
                            return formation != null ? formation : null;
                        } catch (Exception e) {
                            // Gérer l'exception en cas d'échec de l'appel Feign
                            System.err.println("Erreur lors de l'appel Feign pour la formation " + formationId);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull) // Supprime les résultats null
                    .collect(Collectors.toList());

            if (formations.isEmpty()) {
                return ResponseEntity.noContent().build(); // Retourne un statut 204 si aucune formation n'a été récupérée
            }
            return ResponseEntity.ok(formations);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{employerId}/departement")
    public ResponseEntity<Object> getDepartementByEmployerId(@PathVariable Long employerId) {
        Optional<User> employer = employerService.getEmployerById(employerId);
        if (employer.isPresent()) {
            Long departementId = employer.get().getDepartementId();

            try {
                Object departement = departementFeignClient.getDepartementById(departementId);
                return ResponseEntity.ok(departement);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Erreur lors de la récupération du département.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    }

