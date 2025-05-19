package com.hms.app.config;

import com.hms.app.message.Message;
import com.hms.app.message.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String ipAddress = null;

        if (headerAccessor.getSessionAttributes() != null) {
            ipAddress = (String) headerAccessor.getSessionAttributes().get("ipAddress");
        }

        if (ipAddress != null ) {
            log.info("user disconnected: {}", ipAddress);
            var chatMessage = Message.builder()
                    .type(MessageType.DISCONNECT)
                    .ipAddress(ipAddress)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

}