package com.hms.app.services;

import com.hms.app.message.Message;
import com.hms.app.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void storeMetrics(Message message) {
        String docId = new StringBuilder().append(message.getIpAddress())
                .append("_")
                .append(message.getTimeStamp()).toString();
        message.setDocId(docId);
        messageRepository.save(message);
    }
}
