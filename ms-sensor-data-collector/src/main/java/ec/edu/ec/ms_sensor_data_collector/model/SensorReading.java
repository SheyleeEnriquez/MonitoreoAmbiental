package ec.edu.ec.ms_sensor_data_collector.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "sensor_readings")
@Getter
@Setter
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String sensorId;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private Double value;
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
