package com.hms.app.message;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "metrics")
public class Message {
    @Id
    private String docId;
    private String ipAddress;

    private String timeStamp;
    private String sessionId;
    private MessageType type;
    private Double cpuUtilInPercent;

}
