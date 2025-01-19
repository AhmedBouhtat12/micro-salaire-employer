package service.emp.serviceemployer.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.annotation.NonNull;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Employer {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private Long departementId;
    @Enumerated(EnumType.STRING)
    private ContractType contractType;
    @ElementCollection
    private List<Long> formationId;
    @ElementCollection
    private List<Long> absencesId;


    public List<Long> getAbsencesId() {
        return absencesId;
    }

    public void setAbsencesId(List<Long> absencesId) {
        this.absencesId = absencesId;
    }


    public List<Long> getFormationId() {
        return formationId;
    }

    public void setFormationId(List<Long> formationId) {
        this.formationId = formationId;
    }


    public Long getDepartementId() {
        return departementId;
    }

    public void setDepartementId(Long departementId) {
        this.departementId = departementId;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }



}
