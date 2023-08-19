package uz.pdp.citynotificationservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import uz.pdp.citynotificationservice.dto.MailDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaConfig {
    @Autowired
    private ObjectMapper objectMapper;


    @Bean
    public ConsumerFactory<String, MailDto>consumerFactory(){
        JsonDeserializer<MailDto> deserializer = new JsonDeserializer<>(MailDto.class, objectMapper);
        ErrorHandlingDeserializer<MailDto> handlingDeserializer = new ErrorHandlingDeserializer<>(deserializer);
        handlingDeserializer.setFailedDeserializationFunction(info -> {
            log.error("Failed to parse kafka record value to MailDto.class object. Record info: {}", info.toString());
            return null;
        });
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(), new StringDeserializer(), handlingDeserializer);
    }


    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10_000);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 3000);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 20_048_576);
        props.put(ConsumerConfig.RECEIVE_BUFFER_CONFIG, 1_572_864);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-service");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MailDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MailDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
