package com.mitocode.audit_service.config;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.audit_service.service.ClientMongoRepository;
import com.mitocode.audit_service.service.ClientPostgresRepository;
import com.mitocode.audit_service.service.LicenseRepository;
import com.mitocode.audit_service.service.UserRepository;
import com.mitocode.common_models.model.dto.GenericModel;
import com.mitocode.common_models.model.entity.ClientEntity;
import com.mitocode.common_models.model.entity.ClientPostgresEntity;
import com.mitocode.common_models.model.entity.LicenseEntity;
import com.mitocode.common_models.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {


    private final ObjectMapper mapper;
    private final LicenseRepository licenseRepository;
    private final ClientPostgresRepository clientRepository;
    private final ClientMongoRepository clientMongoRepository;
    private final UserRepository userRepository;

    @Value("${kafka.mitocode.server:localhost}")
    private String kafkaServer;

    @Value("${kafka.mitocode.port:9092}")
    private String kafkaPort;

    @Value("${kafka.mitocode.topicName:mitocode}")
    private String topicName;

    @Value("${kafka.mitocode.topicName1:mitocode1}")
    private String topicName1;

    @Bean
    public ConsumerFactory<String, GenericModel<?>> consumerFactory() {
        Map<String, Object> kafkaProps = new HashMap<>();
        kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer + ":" + kafkaPort);
        kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG, topicName);

//        kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        kafkaProps.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, JsonDeserializer.class);
        kafkaProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        kafkaProps.put(JsonDeserializer.KEY_DEFAULT_TYPE, "com.mitocode.audit_service.config.KafkaConfig");
        kafkaProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.mitocode.audit_service.config.KafkaConfig");

        kafkaProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.mitocode.*");

        return new DefaultKafkaConsumerFactory<>(kafkaProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GenericModel<?>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GenericModel<?>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @KafkaListener(topics = "mitocode")
    public void listenTopic(GenericModel<?> message) {

        log.info("MessageMongo: " + message);


        if (message.getClassName().equals(ClientEntity.class.getSimpleName())) {

            ClientEntity clientEntity = mapper.convertValue(message.getT(), new TypeReference<>() {
            });
            log.info("MessageMongo: " + message);

            clientMongoRepository.save(clientEntity);
        }

        if (message.getClassName().equals(UserEntity.class.getSimpleName())) {
            UserEntity userEntity = mapper.convertValue(message.getT(), new TypeReference<>() {
            });
            log.info("MessageMongo: " + message);
            log.info("UserEntity: " + userEntity);
            userRepository.save(userEntity);
        }
        if (message.getClassName().equals(LicenseEntity.class.getSimpleName())) {
            LicenseEntity licenseEntity = mapper.convertValue(message.getT(), new TypeReference<>() {
            });
            log.info("MessageMongo: " + message);
            log.info("license: " + licenseEntity);
            licenseRepository.save(licenseEntity);

        }


    }

    @KafkaListener(topics = "mitocode1")
    public void listenTopic1(GenericModel<?> message) {

        log.info("MessagePostgress: " + message);


        if (message.getClassName().equals(ClientEntity.class.getSimpleName())) {

            ClientEntity clientEntity = mapper.convertValue(message.getT(), new TypeReference<>() {
            });
            log.info("MessagePostgress: " + message);

            ClientPostgresEntity client = new ClientPostgresEntity();

            BeanUtils.copyProperties(clientEntity, client);
            clientRepository.save(client);
        }


    }

}
