package service.emp.serviceformation.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.emp.serviceformation.Entity.Formation;
import service.emp.serviceformation.Repository.FormationRepository;

import java.util.List;
import java.util.Optional;
@Service
public class FormationService {

    private final FormationRepository formationRepository;

    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Formation createFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    public Formation getFormationById(Long id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
    }



    public Formation updateFormation(Long id, Formation updatedFormation) {
        Formation existingFormation = getFormationById(id);
        existingFormation.setTitreFormation(updatedFormation.getTitreFormation());
        existingFormation.setDateDebut(updatedFormation.getDateDebut());
        existingFormation.setDateFin(updatedFormation.getDateFin());
        existingFormation.setCertificationObtenue(updatedFormation.getCertificationObtenue());
        return formationRepository.save(existingFormation);
    }

    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }
//
//    public Formation addEmployeToFormation(Long formationId, Long employeId) {
//        // Récupérer la formation par son ID
//        Formation formation = getFormationById(formationId);
//
//        // Vérifier si l'employé n'est pas déjà dans la formation
//        if (!formation.getEmployeIds().contains(employeId)) {
//            formation.getEmployeIds().add(employeId);  // Ajouter l'employé à la liste
//        }
//
//        // Sauvegarder la formation mise à jour
//        return formationRepository.save(formation);
//    }

}
