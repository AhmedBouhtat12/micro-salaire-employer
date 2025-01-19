package service.emp.serviceabsences.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.emp.serviceabsences.Entity.Absence;

import java.util.List;
@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

}
