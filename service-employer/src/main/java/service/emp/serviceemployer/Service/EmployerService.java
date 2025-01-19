package service.emp.serviceemployer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.emp.serviceemployer.Config.AbsenceRestClient;
import service.emp.serviceemployer.Config.DepartementFeignClient;
import service.emp.serviceemployer.Config.FormationFeignClient;
import service.emp.serviceemployer.Entities.Employer;
import service.emp.serviceemployer.Repository.EmployerRepository;

import java.util.*;

@Service
public class EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private DepartementFeignClient departementFeignClient;
    @Autowired
    private FormationFeignClient formationFeignClient;
    @Autowired
    private AbsenceRestClient absenceRestClient;

    public Employer createEmployer(Employer employer) {
        return employerRepository.save(employer);
    }


    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    public Optional<Employer> getEmployerById(Long id) {
        return employerRepository.findById(id);
    }

    public Employer updateEmployer(Long id, Employer updatedEmployer) {
        Optional<Employer> optionalEmployer = employerRepository.findById(id);
        if (optionalEmployer.isPresent()) {
            Employer employer = optionalEmployer.get();
            employer.setNom(updatedEmployer.getNom());
            employer.setPrenom(updatedEmployer.getPrenom());
            employer.setEmail(updatedEmployer.getEmail());
            employer.setAdresse(updatedEmployer.getAdresse());
            employer.setTelephone(updatedEmployer.getTelephone());
            employer.setDepartementId(updatedEmployer.getDepartementId());
            return employerRepository.save(employer);
        }
        return null; // ou lever une exception
    }

    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }

    public Object getEmployerWithDepartement(Long employerId) {
        Optional<Employer> employer = employerRepository.findById(employerId);
        if (employer.isPresent()) {
            Object departement = departementFeignClient.getDepartementById(employer.get().getDepartementId());
            return new Object() {
                public Employer employe = employer.get();
                public Object departementInfo = departement;
            };
        } else {
            throw new RuntimeException("Employé introuvable avec l'ID : " + employerId);
        }
    }
    public Employer addFormationToEmployer(Long employerId, Long formationId) {
        // Récupérer l'employé par ID
        Optional<Employer> employerOptional = employerRepository.findById(employerId);
        if (employerOptional.isPresent()) {
            Employer employer = employerOptional.get();

            // Récupérer les informations de la formation via FeignClient
            Object formation = formationFeignClient.getFormationById(formationId);
            if (formation != null && !employer.getFormationId().contains(formationId)) {
                // Ajouter l'ID de la formation à la liste des formations de l'employé
                employer.getFormationId().add(formationId);

                // Sauvegarder l'employé mis à jour
                return employerRepository.save(employer);
            }
        }
        return null;
    }
    public Employer addAbsenceToEmployer(Long employerId, Long absenceId) {
        Optional<Employer> employerOptional = employerRepository.findById(employerId);
        if (employerOptional.isPresent()) {
            Employer employer = employerOptional.get();
            Object absence = absenceRestClient.getAbsenceById(absenceId);
            if (absence != null && !employer.getAbsencesId().contains(absenceId)) {

                employer.getAbsencesId().add(absenceId);

                return employerRepository.save(employer);
            }
        }
        return null;
    }

    public Map<String, Object> getEmployerWithFormation(Long employerId) {
        Optional<Employer> employer = employerRepository.findById(employerId);
        if (employer.isPresent()) {
            // Get formation details for the employer's formations
            List<Object> formations = new ArrayList<>();
            for (Long formationId : employer.get().getFormationId()) {
                Object formation = formationFeignClient.getFormationById(formationId);
                formations.add(formation);
            }

            // Create a map to return employer and their formations
            Map<String, Object> response = new HashMap<>();
            response.put("employer", employer.get());
            response.put("formations", formations);

            return response;
        } else {
            throw new RuntimeException("Employer not found with ID: " + employerId);
        }
    }


    public Employer removeFormationFromEmployer(Long employerId, Long formationId) {
        Optional<Employer> employerOptional = employerRepository.findById(employerId);
        if (employerOptional.isPresent()) {
            Employer employer = employerOptional.get();

            List<Long> formationIds = employer.getFormationId();
            formationIds.remove(formationId);
            employer.setFormationId(formationIds);


            return employerRepository.save(employer);
        }
        return null;
    }


    public Employer removeAbsenceFromEmployer(Long employerId, Long absenceId) {
        Optional<Employer> employerOptional = employerRepository.findById(employerId);
        if (employerOptional.isPresent()) {
            Employer employer = employerOptional.get();

            List<Long> absencesId = employer.getAbsencesId();
            absencesId.remove(absenceId);
            employer.setAbsencesId(absencesId);

            return employerRepository.save(employer);
        }
        return null;
    }


}
