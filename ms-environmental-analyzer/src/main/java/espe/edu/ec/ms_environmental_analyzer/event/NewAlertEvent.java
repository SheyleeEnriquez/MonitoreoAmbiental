package espe.edu.ec.ms_environmental_analyzer.event;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewAlertEvent {
    private String eventId;
    private String type;
    private String sensorId;
    private Double value;
    private Double threshold;
    private LocalDateTime timestamp;
}
