package service.emp.serviceformation.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.emp.serviceformation.Entity.Formation;
import service.emp.serviceformation.Service.FormationService;

import java.util.List;
@RestController
@RequestMapping("/api/formations")
public class FormationController {

    @Autowired
    private  FormationService formationService;



    @PostMapping
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) {
        return ResponseEntity.ok(formationService.createFormation(formation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        return ResponseEntity.ok(formationService.getFormationById(id));
    }

    @GetMapping
    public ResponseEntity<List<Formation>> getAllFormations() {
        return ResponseEntity.ok(formationService.getAllFormations());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        return ResponseEntity.ok(formationService.updateFormation(id, formation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }





}
