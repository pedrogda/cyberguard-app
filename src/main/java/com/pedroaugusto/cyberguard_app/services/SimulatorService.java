package com.pedroaugusto.cyberguard_app.services;

import com.pedroaugusto.cyberguard_app.model.EventType;
import com.pedroaugusto.cyberguard_app.model.SecurityEvent;
import org.springframework.stereotype.Service;

@Service
public class SimulatorService {

    private final EventService eventService;

    public SimulatorService(EventService eventService) {
        this.eventService = eventService;
    }

    public void simulateBruteForce() {

        for (int i = 0; i < 5; i++) {

            SecurityEvent event = new SecurityEvent();

            event.setEventType(EventType.LOGIN_FAILED);
            event.setUsername("admin");
            event.setSourceIp("192.168.1.100");
            event.setDeviceName("SIMULATOR-PC");

            eventService.createEvent(event);
        }
    }

    public void simulatePasswordSpraying() {

        String[] users = {
                "pedro",
                "maria",
                "admin",
                "financeiro",
                "suporte"
        };

        for (String username : users) {

            SecurityEvent event = new SecurityEvent();

            event.setEventType(EventType.LOGIN_FAILED);
            event.setUsername(username);
            event.setSourceIp("192.168.1.200");
            event.setDeviceName("SIMULATOR-PC");

            eventService.createEvent(event);
        }
    }

    public void simulateAccountCompromise() {

        for (int i = 0; i < 5; i++) {

            SecurityEvent event = new SecurityEvent();

            event.setEventType(EventType.LOGIN_FAILED);
            event.setUsername("financeiro");
            event.setSourceIp("192.168.1.250");
            event.setDeviceName("FINANCE-PC");

            eventService.createEvent(event);
        }

        SecurityEvent successEvent = new SecurityEvent();

        successEvent.setEventType(EventType.LOGIN_SUCCESS);
        successEvent.setUsername("financeiro");
        successEvent.setSourceIp("192.168.1.250");
        successEvent.setDeviceName("FINANCE-PC");

        eventService.createEvent(successEvent);
    }

}
