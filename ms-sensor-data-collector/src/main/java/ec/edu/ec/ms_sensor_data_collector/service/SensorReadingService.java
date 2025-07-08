package ec.edu.ec.ms_sensor_data_collector.service;

import ec.edu.ec.ms_sensor_data_collector.dto.ResponseDto;
import ec.edu.ec.ms_sensor_data_collector.dto.SensorReadingDto;
import ec.edu.ec.ms_sensor_data_collector.event.NewSensorReadingEvent;
import ec.edu.ec.ms_sensor_data_collector.model.SensorReading;
import ec.edu.ec.ms_sensor_data_collector.repository.SensorReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SensorReadingService {
    @Autowired
    private SensorReadingRepository sensorReadingRepository;

    @Autowired
    private EventPublisherService eventPublisherService;

    public ResponseDto processSensorReading(SensorReadingDto sensorReadingDTO) {
        //Validar el dto
        validateSensorReading(sensorReadingDTO);

        //Cambiar de sensorReadingDto a sensorReading
        SensorReading sensorReading = new SensorReading();
        sensorReading.setSensorId(sensorReadingDTO.getSensorId());
        sensorReading.setType(sensorReadingDTO.getType());
        sensorReading.setValue(sensorReadingDTO.getValue());
        sensorReading.setTimestamp(sensorReadingDTO.getTimestamp());

        //Guardar lectura en la bd
        SensorReading savedSensorReading = sensorReadingRepository.save(sensorReading);

        //Enviar el evento
        NewSensorReadingEvent event = new NewSensorReadingEvent(
                UUID.randomUUID().toString(),
                savedSensorReading.getSensorId(),
                savedSensorReading.getType(),
                savedSensorReading.getValue(),
                savedSensorReading.getTimestamp()
        );

        eventPublisherService.publishEvent(event);

        return new ResponseDto("Lectura de sensor registrada exitosamente", savedSensorReading);
    }

    public List<SensorReadingDto> getSensorHistory() {
        List<SensorReading> readings = sensorReadingRepository.findAll();
        return readings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void validateSensorReading(SensorReadingDto dto) {
        if (dto.getSensorId() == null || dto.getSensorId().isEmpty()) {
            throw new IllegalArgumentException("Sensor ID is required");
        }
        if (dto.getType() == null || dto.getType().isEmpty()) {
            throw new IllegalArgumentException("Sensor type is required");
        }
        if (dto.getValue() == 0.0) {
            throw new IllegalArgumentException("Sensor value is required");
        }

        // Validar rangos específicos
        if ("temperature".equals(dto.getType()) && dto.getValue() > 60.0) {
            throw new IllegalArgumentException("Temperature value out of range");
        }

        if ("humedad".equals(dto.getType()) && (dto.getValue() > 100.0 || dto.getValue() < 0.0)) {
            throw new IllegalArgumentException("Humidity value out of range");
        }

        if (dto.getTimestamp() == null) {
            throw new IllegalArgumentException("Timestamp is required");
        }
    }

    private SensorReadingDto convertToDTO(SensorReading reading) {
        return new SensorReadingDto(
                reading.getSensorId(),
                reading.getType(),
                reading.getValue(),
                reading.getTimestamp()
        );
    }
}
