package ec.edu.ec.ms_sensor_data_collector.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "sensor.events";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable("sensor.readings.queue").build();
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("sensor.reading.new");
    }
}
