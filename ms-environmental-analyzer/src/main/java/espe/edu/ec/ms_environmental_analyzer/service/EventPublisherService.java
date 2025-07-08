package espe.edu.ec.ms_environmental_analyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import espe.edu.ec.ms_environmental_analyzer.event.NewAlertEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EventPublisherService {
    private static final String EXCHANGE_NAME = "analyzer.events";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // Almacenamiento local para cuando RabbitMQ esté caído
    private List<NewAlertEvent> pendingEvents = new ArrayList<>();
    private boolean isRabbitMQAvailable = true;

    public void publishEvent(NewAlertEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "enviromental.analysis.new", eventJson);
            log.info("Event published: {}", event.getEventId());

            // Si RabbitMQ está disponible y hay eventos pendientes, reenviarlos
            if (isRabbitMQAvailable && !pendingEvents.isEmpty()) {
                reprocessPendingEvents();
            }

        } catch (Exception e) {
            log.error("Failed to publish event, storing locally: {}", e.getMessage());
            isRabbitMQAvailable = false;
            pendingEvents.add(event);
        }
    }

    private void reprocessPendingEvents() {
        log.info("Reprocessing {} pending events", pendingEvents.size());
        List<NewAlertEvent> eventsToProcess = new ArrayList<>(pendingEvents);
        pendingEvents.clear();

        for (NewAlertEvent event : eventsToProcess) {
            try {
                String eventJson = objectMapper.writeValueAsString(event);
                rabbitTemplate.convertAndSend(EXCHANGE_NAME, "enviromental.analysis.new", eventJson);
                log.info("Pending event reprocessed: {}", event.getEventId());
            } catch (Exception e) {
                log.error("Failed to reprocess pending event: {}", e.getMessage());
                pendingEvents.add(event);
            }
        }

        if (pendingEvents.isEmpty()) {
            isRabbitMQAvailable = true;
        }
    }
}
