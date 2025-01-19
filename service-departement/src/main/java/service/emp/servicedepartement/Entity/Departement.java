package service.emp.servicedepartement.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    @ElementCollection
    private List<Long> employeIds;


    public List<Long> getEmployeIds() {
        return employeIds;
    }

    public void setEmployeIds(List<Long> employeIds) {
        this.employeIds = employeIds;
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


}
