package ec.edu.ec.ms_sensor_data_collector.repository;

import ec.edu.ec.ms_sensor_data_collector.model.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {
}
