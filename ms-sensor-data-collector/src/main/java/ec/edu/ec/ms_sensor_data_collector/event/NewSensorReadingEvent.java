package ec.edu.ec.ms_sensor_data_collector.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewSensorReadingEvent {
    private String eventId;
    private String sensorId;
    private String type;
    private Double value;
    private LocalDateTime timestamp;
}
