package service.emp.servicedepartement.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.emp.servicedepartement.Entity.Departement;
import service.emp.servicedepartement.Repository.DepartementRepository;
import service.emp.servicedepartement.config.EmployeFeignClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private EmployeFeignClient employeFeignClient;
    // Create or Update a Departement
    public Departement saveDepartement(Departement departement) {
        departement.setId(departement.getId());
        departement.setNom(departement.getNom());
        return departementRepository.save(departement);
    }

    // Read all Departements
    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }

    // Read a Departement by ID
    public Optional<Departement> getDepartementById(Long id) {
        return departementRepository.findById(id);
    }

    // Delete a Departement
    public void deleteDepartement(Long id) {
        departementRepository.deleteById(id);
    }
    public Departement getDepartementWithEmployes(Long departementId) {
        Departement departement = departementRepository.findById(departementId)
                .orElseThrow(() -> new RuntimeException("Département introuvable"));

        // Récupération des informations des employés
        List<Object> employes = departement.getEmployeIds().stream()
                .map(employeFeignClient::getEmployeById)
                .collect(Collectors.toList());

        // Ajouter la logique pour inclure les employés dans la réponse si nécessaire
        return departement;
    }


}
