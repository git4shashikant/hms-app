package com.hms.app.broker;

import com.hms.app.message.Message;
import com.hms.app.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private final MessageService messageService;

    @Autowired
    public Receiver(MessageService messageService) {
        this.messageService = messageService;
    }

    @KafkaListener(topics = "${kafka.hms.metrics.topic}", groupId = "${spring.kafka.consumer.matrix.group_id}")
    public void consume(Message message) {
        logger.info("Received message from Kafka: " + message.getCpuUtilInPercent());
        messageService.storeMetrics(message);
    }
}