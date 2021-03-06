package com.corelogic.hazardbot;

import com.corelogic.hazardbot.notification.smsclient.EventType;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Event {
    private String content;
    private EventType eventType;

    public Event(String content) {
        this.content = content;
        this.eventType = EventType.CLOWN;
    }
}
