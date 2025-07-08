package espe.edu.ec.ms_environmental_analyzer.listener;

import espe.edu.ec.ms_environmental_analyzer.event.NewSensorReadingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventsListener {
    @RabbitListener(queues = "sensor.readings.queue")
    public void newSensorReading(NewSensorReadingEvent sensorReading) {

    }
}
