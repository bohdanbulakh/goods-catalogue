package org.example.goodscatalogue.components;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Scope("prototype")
public class RequestTimer {
    private final String timestamp;

    public RequestTimer() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public String getTimestamp() {
        return timestamp;
    }
}