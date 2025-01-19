package service.emp.serviceemployer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.emp.serviceemployer.Entities.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
}
