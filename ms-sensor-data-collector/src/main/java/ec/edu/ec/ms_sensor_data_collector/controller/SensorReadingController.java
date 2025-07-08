package ec.edu.ec.ms_sensor_data_collector.controller;

import ec.edu.ec.ms_sensor_data_collector.dto.ResponseDto;
import ec.edu.ec.ms_sensor_data_collector.dto.SensorReadingDto;
import ec.edu.ec.ms_sensor_data_collector.service.SensorReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SensorReadingController {

    @Autowired
    private SensorReadingService sensorReadingService;

    @PostMapping
    public ResponseDto addSensorReading(@RequestBody SensorReadingDto sensorReadingDto) {
        return sensorReadingService.processSensorReading(sensorReadingDto);
    }

    @GetMapping
    public List<SensorReadingDto> listarLecturasDeLosSensores() {
        return sensorReadingService.getSensorHistory();
    }
}
