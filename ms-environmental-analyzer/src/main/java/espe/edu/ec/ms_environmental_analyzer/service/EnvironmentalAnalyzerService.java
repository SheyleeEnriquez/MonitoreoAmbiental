package espe.edu.ec.ms_environmental_analyzer.service;

import espe.edu.ec.ms_environmental_analyzer.event.NewAlertEvent;
import espe.edu.ec.ms_environmental_analyzer.event.NewSensorReadingEvent;
import espe.edu.ec.ms_environmental_analyzer.model.Alert;
import espe.edu.ec.ms_environmental_analyzer.repository.AlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class EnvironmentalAnalyzerService {
    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private EventPublisherService eventPublisherService;

    public void analyze(NewSensorReadingEvent event) {
        if(event.getType().equals("tempeture")){
            if(event.getValue() > 40.0) createAlarm(event, 40.0);
        }else if(event.getType().equals("humidity")){
            if(event.getValue() < 20.0) createAlarm(event, 20.0);
        }else if(event.getType().equals("seismic-activity")){
            if(event.getValue() > 3.0) createAlarm(event, 3.0);
        }else{
            log.error("Sensor type not recognized");
        }
    }

    private void createAlarm(NewSensorReadingEvent event, Double threshold){
        Alert alert = new Alert();
        alert.setType(event.getType());
        alert.setSensorId(event.getSensorId());
        alert.setValue(event.getValue());
        alert.setThreshold(threshold);
        alert.setTimestamp(event.getTimestamp());

        log.info("Model alert created");

        //Guardar alerta en la bd
        Alert savedAlert = alertRepository.save(alert);

        log.info("Alert saved in the db");

        //Enviar el evento
        NewAlertEvent newAlertEvent = new NewAlertEvent(
                UUID.randomUUID().toString(),
                savedAlert.getType(),
                savedAlert.getSensorId(),
                savedAlert.getValue(),
                savedAlert.getThreshold(),
                savedAlert.getTimestamp()
        );

        log.info("Model event alert created");

        //enviar el evento
        eventPublisherService.publishEvent(newAlertEvent);
    }
}
