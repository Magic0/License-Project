package com.mitocode.clientqueryservice.util;

import com.mitocode.common_models.model.dto.GenericModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaUtil {

    @Value("${kafka.mitocode.topicName:mitocode}")
    private String topicName;

    @Value("${kafka.mitocode.topicName1:mitocode1}")
    private String topicName1;

    private final KafkaTemplate<String, GenericModel<?>> kafkaTemplate;

    public void sendMessageAuditory(GenericModel<?> message) {
        kafkaTemplate.send(topicName, message);
    }

    public void sendMessagePersistence(GenericModel<?> message) {
        kafkaTemplate.send(topicName1, message);
    }
}
