package service.emp.servicedepartement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.emp.servicedepartement.Entity.Departement;
import service.emp.servicedepartement.Service.DepartementService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departements")
public class DepartementController {

    @Autowired
    private DepartementService departementService;


    @PostMapping
    public ResponseEntity<Departement> createOrUpdateDepartement(@RequestBody Departement departement) {
        return ResponseEntity.ok(departementService.saveDepartement(departement));
    }

    // Read all Departements
    @GetMapping
    public List<Departement> getAllDepartements() {
        return departementService.getAllDepartements();
    }

    // Read a Departement by ID
    @GetMapping("/{id}")
    public Optional<Departement> getDepartementById(@PathVariable Long id) {
        return departementService.getDepartementById(id);
    }

    // Delete a Departement
    @DeleteMapping("/{id}")
    public void deleteDepartement(@PathVariable Long id) {
        departementService.deleteDepartement(id);
    }

    @GetMapping("/{id}/details")
    public Departement getDepartementDetails(@PathVariable Long id) {
        return departementService.getDepartementWithEmployes(id);
    }

}