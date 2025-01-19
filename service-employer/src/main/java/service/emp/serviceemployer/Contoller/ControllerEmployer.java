package service.emp.serviceemployer.Contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.emp.serviceemployer.Entities.Absence;
import service.emp.serviceemployer.Entities.Employer;
import service.emp.serviceemployer.Entities.FormationDto;
import service.emp.serviceemployer.Service.EmployerService;
import service.emp.serviceemployer.Config.DepartementFeignClient;
import service.emp.serviceemployer.Config.FormationFeignClient;
import service.emp.serviceemployer.Config.AbsenceRestClient;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employers")
public class ControllerEmployer {

    @Autowired
    private EmployerService employerService;
    @Autowired
    private DepartementFeignClient departementFeignClient;
    @Autowired
    private FormationFeignClient formationFeignClient;
    @Autowired
    private AbsenceRestClient absenceRestClient;

    @PostMapping("/add")
    public ResponseEntity<Employer> createEmployer(@RequestBody Employer employer) {
        Employer savedEmployer = employerService.createEmployer(employer);
        return ResponseEntity.ok(savedEmployer);
    }

    @GetMapping
    public ResponseEntity<List<Employer>> getAllEmployers() {
        List<Employer> employers = employerService.getAllEmployers();
        return ResponseEntity.ok(employers);
    }

    @GetMapping("/{id}")
    public Optional<Employer> getEmployerById(@PathVariable Long id) {
        return employerService.getEmployerById(id);
    }

    @PutMapping("/{id}")
    public Employer updateEmployer(@PathVariable Long id, @RequestBody Employer employer) {
        return employerService.updateEmployer(id, employer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        employerService.deleteEmployer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/details")
    public Object getEmployerWithDepartement(@PathVariable Long id) {
        return employerService.getEmployerWithDepartement(id);
    }

    @PutMapping("/{employerId}/formations/{formationId}")
    public ResponseEntity<Employer> addFormationToEmployer(@PathVariable Long employerId, @PathVariable Long formationId) {
        try {
            Employer updatedEmployer = employerService.addFormationToEmployer(employerId, formationId);
            if (updatedEmployer != null) {
                return ResponseEntity.ok(updatedEmployer);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Gérer l'exception ici si quelque chose échoue lors de l'ajout de la formation
            System.err.println("Erreur lors de l'ajout de la formation à l'employé : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{employerId}/absences/{absencesId}")
    public ResponseEntity<Employer> addAbsenceToEmployer(@PathVariable Long employerId, @PathVariable Long absencesId) {
        Employer updatedEmployer = employerService.addAbsenceToEmployer(employerId, absencesId);
        if (updatedEmployer != null) {
            return ResponseEntity.ok(updatedEmployer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/details")
    public ResponseEntity<List<Object>> getAllEmployersDetails() {
        List<Employer> employers = employerService.getAllEmployers();
        if (employers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Object> employersDetails = Collections.singletonList(employers.stream().map(employer -> {
            Object departement = null;
            List<FormationDto> formations = null;
            List<Absence> absences = null;

            try {
                departement = departementFeignClient.getDepartementById(employer.getDepartementId());
            } catch (Exception e) {
                departement = "Erreur lors de la récupération du département.";
            }

            try {
                formations = employer.getFormationId().stream()
                        .map(formationId -> formationFeignClient.getFormationById(formationId))
                        .toList();
            } catch (Exception e) {
                formations = List.of();  // Liste vide en cas d'erreur
            }

            try {
                absences = absenceRestClient.getAllAbsence().stream()
                        .filter(absence -> absence.getEmployerId().equals(employer.getId()))
                        .toList();
            } catch (Exception e) {
                absences = List.of();  // Liste vide en cas d'erreur
            }

            // Création de la carte avec HashMap
            HashMap<Object, Object> details = new HashMap<>();
            details.put("Employer", employer);
            details.put("Departement", departement);
            details.put("Formations", formations);
            details.put("Absences", absences);

            return details;
        }).toList());

        return ResponseEntity.ok(employersDetails);
    }
    // 1. Obtenir les absences d'un employé par son ID
    @GetMapping("/{employerId}/absences")
    public ResponseEntity<List<Absence>> getAbsencesByEmployerId(@PathVariable Long employerId) {
        Optional<Employer> employer = employerService.getEmployerById(employerId);
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


    // 2. Obtenir les formations d'un employé par son ID
    @GetMapping("/{employerId}/formations")
    public ResponseEntity<List<FormationDto>> getFormationsByEmployerId(@PathVariable Long employerId) {
        Optional<Employer> employer = employerService.getEmployerById(employerId);
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


    // 3. Obtenir le département d'un employé par son ID
    @GetMapping("/{employerId}/departement")
    public ResponseEntity<Object> getDepartementByEmployerId(@PathVariable Long employerId) {
        Optional<Employer> employer = employerService.getEmployerById(employerId);
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

    @DeleteMapping("/{employerId}/formations/{formationId}")
    public ResponseEntity<Employer> removeFormationFromEmployer(@PathVariable Long employerId, @PathVariable Long formationId) {
        try {
            Employer updatedEmployer = employerService.removeFormationFromEmployer(employerId, formationId);
            if (updatedEmployer != null) {
                return ResponseEntity.ok(updatedEmployer);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de la formation : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{employerId}/Absence/{absenceId}")
    public ResponseEntity<Employer> removeAbsenceFromEmployer(@PathVariable Long employerId, @PathVariable Long absenceId) {
        try {
            Employer updatedEmployer = employerService.removeAbsenceFromEmployer(employerId, absenceId);
            if (updatedEmployer != null) {
                return ResponseEntity.ok(updatedEmployer);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Use a logging framework for production-level logging
            System.err.println("Erreur lors de la suppression de la absence : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}


