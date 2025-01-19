package service.emp.serviceformation.Entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titreFormation;
    private Date dateDebut;
    private Date dateFin;
    private String certificationObtenue;
    @ElementCollection
    private List<Long> employeIds;

    public List<Long> getEmployeIds() {
        return employeIds;
    }

    public void setEmployeIds(List<Long> employeIds) {
        this.employeIds = employeIds;
    }


    public String getCertificationObtenue() {
        return certificationObtenue;
    }

    public void setCertificationObtenue(String certificationObtenue) {
        this.certificationObtenue = certificationObtenue;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreFormation() {
        return titreFormation;
    }

    public void setTitreFormation(String titreFormation) {
        this.titreFormation = titreFormation;
    }


}
