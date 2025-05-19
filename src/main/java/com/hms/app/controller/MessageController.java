package com.hms.app.controller;

import com.hms.app.broker.Sender;
import com.hms.app.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @Value("${kafka.hms.metrics.topic}")
    private String metricsTopic;
    private final Sender sender;

    @Autowired
    public MessageController(Sender sender) {
        this.sender = sender;
    }

    @MessageMapping("/message.send-metrics")
    public void sendMetrics(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        message.setSessionId(headerAccessor.getSessionId());
        if (headerAccessor.getSessionAttributes() != null) {
            message.setIpAddress((String)headerAccessor.getSessionAttributes().get("ipAddress"));
        }
        sender.send(metricsTopic, message);
    }

    @MessageMapping("/message.add-ip")
    @SendTo("/topic/public")
    public Message addIP(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        message.setSessionId(headerAccessor.getSessionId());
        if (headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("ipAddress", message.getIpAddress());
        }

        return message;
    }
}