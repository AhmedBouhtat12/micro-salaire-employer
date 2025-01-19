package service.emp.serviceabsences.Service;
import org.springframework.stereotype.Service;
import service.emp.serviceabsences.Entity.Absence;
import service.emp.serviceabsences.Repository.AbsenceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;

    public AbsenceService(AbsenceRepository absenceRepository) {
        this.absenceRepository = absenceRepository;
    }

    // Récupérer toutes les absences
    public List<Absence> getAllAbsence() {
        return absenceRepository.findAll();
    }

    // Récupérer une absence par ID
    public Absence getById(Long id) {
        Optional<Absence> absenceOptional = absenceRepository.findById(id);
        return absenceOptional.orElse(null);
    }

    // Ajouter une nouvelle absence
    public Absence addAbsence(Absence absence) {
        return absenceRepository.save(absence);
    }

    // Modifier une absence existante
    public Absence updateAbsence(Long id, Absence updatedAbsence) {
        Optional<Absence> existingAbsenceOptional = absenceRepository.findById(id);
        if (existingAbsenceOptional.isPresent()) {
            Absence existingAbsence = existingAbsenceOptional.get();
            existingAbsence.setType(updatedAbsence.getType());
            existingAbsence.setStartDate(updatedAbsence.getStartDate());
            existingAbsence.setEndDate(updatedAbsence.getEndDate());
            existingAbsence.setStatus(updatedAbsence.getStatus());
            existingAbsence.setManagerComments(updatedAbsence.getManagerComments());
            return absenceRepository.save(existingAbsence);
        }
        return null;
    }

    // Supprimer une absence par ID
    public void deleteAbsence(Long id) {
        absenceRepository.deleteById(id);
    }
}
