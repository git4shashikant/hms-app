package com.hms.app.controller;

import com.hms.app.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@RestController
public class CommandController {
    @Value("${kafka.hms.metrics.topic}")
    private String metricsTopic;
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public CommandController(KafkaTemplate<String, Message> kafkaTemplate, SimpMessageSendingOperations messagingTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/send")
    public void send(@RequestBody Message message) {
        kafkaTemplate.send(metricsTopic, message);
        messagingTemplate.convertAndSend("/topic/public", message);
    }
}