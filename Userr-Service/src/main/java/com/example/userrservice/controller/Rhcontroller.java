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
public class Rhcontroller {

    @Autowired
    private EmployerService employerService;

    @Autowired
    private DepartementFeignClient departementFeignClient;

    @Autowired
    private FormationFeignClient formationFeignClient;

    @Autowired
    private AbsenceRestClient absenceRestClient;


    @GetMapping("/{username}/absences")
    public ResponseEntity<List<Absence>> getAbsencesByUsername(@PathVariable String username) {
        Optional<User> employer = employerService.getEmployerByUsername(username);
        ²   if (employer.isPresent()) {
            List<Long> absencesId = employer.get().getAbsencesId();

            List<Absence> absences = absencesId.stream()
                    .map(absenceRestClient::getAbsenceById)
                    .toList();
            return ResponseEntity.ok(absences);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{username}/formations")
    public ResponseEntity<List<FormationDto>> getFormationsByUsername(@PathVariable String username) {
        Optional<User> employer = employerService.getEmployerByUsername(username);
        if (employer.isPresent()) {
            List<Long> formationIds = employer.get().getFormationId();

            List<FormationDto> formations = formationIds.stream()
                    .map(formationId -> {
                        try {
                            FormationDto formation = formationFeignClient.getFormationById(formationId);
                            return formation != null ? formation : null;
                        } catch (Exception e) {
                            System.err.println("Erreur lors de l'appel Feign pour la formation " + formationId);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (formations.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(formations);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{username}/departement")
    public ResponseEntity<Object> getDepartementByUsername(@PathVariable String username) {
        Optional<User> employer = employerService.getEmployerByUsername(username);
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
    @GetMapping("/{username}/profile")
    public ResponseEntity<User> getProfileByUsername(@PathVariable String username) {
        // Récupérer l'utilisateur par son username
        Optional<User> employer = employerService.getEmployerByUsername(username);

        if (employer.isPresent()) {
            // Retourner les informations de l'utilisateur sous forme de réponse
            return ResponseEntity.ok(employer.get());
        } else {
            // Si l'utilisateur n'est pas trouvé, retourner une réponse "Not Found"
            return ResponseEntity.notFound().build();
        }
    }

}
