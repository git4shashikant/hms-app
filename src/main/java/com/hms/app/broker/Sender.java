package com.hms.app.broker;

import com.hms.app.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sender {
    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    public Sender(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, Message message) {
        kafkaTemplate.send(topic, message);
    }
}