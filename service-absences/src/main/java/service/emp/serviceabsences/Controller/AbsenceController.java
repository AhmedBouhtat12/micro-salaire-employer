package service.emp.serviceabsences.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.emp.serviceabsences.Entity.Absence;
import service.emp.serviceabsences.Service.AbsenceService;

import java.util.List;


@RestController
@RequestMapping("/api/absences") // Base path
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    // Récupérer une absence par ID
    @GetMapping("/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable Long id) {
        Absence absence = absenceService.getById(id);
        return absence != null ? ResponseEntity.ok(absence) : ResponseEntity.notFound().build();
    }

    // Récupérer toutes les absences
    @GetMapping
    public List<Absence> getAllAbsence() {
        return absenceService.getAllAbsence();
    }

    // Ajouter une nouvelle absence
    @PostMapping
    public ResponseEntity<Absence> addAbsence(@Valid @RequestBody Absence absence) {
        Absence createdAbsence = absenceService.addAbsence(absence);
        return ResponseEntity.ok(createdAbsence);
    }

    // Modifier une absence existante
    @PutMapping("/{id}")
    public ResponseEntity<Absence> updateAbsence(
            @PathVariable Long id,
            @Valid @RequestBody Absence updatedAbsence) {
        Absence updated = absenceService.updateAbsence(id, updatedAbsence);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Supprimer une absence
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
        return ResponseEntity.noContent().build();
    }
}
