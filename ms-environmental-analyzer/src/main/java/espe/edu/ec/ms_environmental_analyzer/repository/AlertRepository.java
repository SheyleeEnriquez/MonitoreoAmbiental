package espe.edu.ec.ms_environmental_analyzer.repository;

import espe.edu.ec.ms_environmental_analyzer.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}
