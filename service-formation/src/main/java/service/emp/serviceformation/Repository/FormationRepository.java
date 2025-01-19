package service.emp.serviceformation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.emp.serviceformation.Entity.Formation;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
}