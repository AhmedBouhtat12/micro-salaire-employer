package service.emp.servicedepartement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.emp.servicedepartement.Entity.Departement;

public interface DepartementRepository  extends JpaRepository<Departement, Long> {
}
