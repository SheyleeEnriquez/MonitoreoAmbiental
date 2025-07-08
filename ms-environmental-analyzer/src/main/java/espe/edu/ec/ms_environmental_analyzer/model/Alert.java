package espe.edu.ec.ms_environmental_analyzer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "alerts")
@Getter
@Setter
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String sensorId;
    @Column(nullable = false)
    private Double value;
    @Column(nullable = false)
    private Double threshold;
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
