package com.example.userrservice.service;





import com.example.userrservice.Configg.AbsenceRestClient;
import com.example.userrservice.Configg.DepartementFeignClient;
import com.example.userrservice.Configg.FormationFeignClient;
import com.example.userrservice.entities.User;
import com.example.userrservice.repository.EmployerRepository;
import com.example.userrservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    @Autowired
    private UserRepository userRepository;

    public User createEmployer(User user) {
        return employerRepository.save(user);
    }


    public List<User> getAllEmployers() {
        return employerRepository.findAll();
    }

    public Optional<User> getEmployerById(Long id) {
        return employerRepository.findById(id);
    }

    public User updateEmployer(Long id, User updatedEmployer) {
        Optional<User> optionalEmployer = employerRepository.findById(id);
        if (optionalEmployer.isPresent()) {
            User employer = optionalEmployer.get();

            employer.setNom(updatedEmployer.getNom());
            employer.setPrenom(updatedEmployer.getPrenom());
            employer.setEmail(updatedEmployer.getEmail());
            employer.setAddress(updatedEmployer.getAddress());
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
        Optional<User> employer = employerRepository.findById(employerId);
        if (employer.isPresent()) {
            Object departement = departementFeignClient.getDepartementById(employer.get().getDepartementId());
            return new Object() {
                public User employe = employer.get();
                public Object departementInfo = departement;
            };
        } else {
            throw new RuntimeException("Employé introuvable avec l'ID : " + employerId);
        }
    }
    public User addFormationToEmployer(Long employerId, Long formationId) {
        // Récupérer l'employé par ID
        Optional<User> employerOptional = employerRepository.findById(employerId);
        if (employerOptional.isPresent()) {
            User employer = employerOptional.get();

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
    public User addAbsenceToEmployer(Long employerId, Long absenceId) {
        Optional<User> employerOptional = employerRepository.findById(employerId);
        if (employerOptional.isPresent()) {
            User employer = employerOptional.get();
            Object absence = absenceRestClient.getAbsenceById(absenceId);
            if (absence != null && !employer.getAbsencesId().contains(absenceId)) {

                employer.getAbsencesId().add(absenceId);

                return employerRepository.save(employer);
            }
        }
        return null;
    }

    public Map<String, Object> getEmployerWithFormation(Long employerId) {
        Optional<User> employer = employerRepository.findById(employerId);
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


    public User removeFormationFromEmployer(Long employerId, Long formationId) {
        Optional<User> employerOptional = employerRepository.findById(employerId);
        if (employerOptional.isPresent()) {
            User employer = employerOptional.get();

            List<Long> formationIds = employer.getFormationId();
            formationIds.remove(formationId);
            employer.setFormationId(formationIds);


            return employerRepository.save(employer);
        }
        return null;
    }


    public User removeAbsenceFromEmployer(Long employerId, Long absenceId) {
        Optional<User> employerOptional = employerRepository.findById(employerId);
        if (employerOptional.isPresent()) {
            User employer = employerOptional.get();

            List<Long> absencesId = employer.getAbsencesId();
            absencesId.remove(absenceId);
            employer.setAbsencesId(absencesId);

            return employerRepository.save(employer);
        }
        return null;
    }



    public Optional<User> getEmployerByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
