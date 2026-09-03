package com.pedroaugusto.cyberguard_app.services;

import com.pedroaugusto.cyberguard_app.model.EventType;
import com.pedroaugusto.cyberguard_app.model.SecurityEvent;
import com.pedroaugusto.cyberguard_app.repository.SecurityEventRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
public class DetectionService {
    private final SecurityEventRepository eventRepository;
    private final AlertService alertService;

    public DetectionService(
            SecurityEventRepository eventRepository,
            AlertService alertService) {

        this.eventRepository = eventRepository;
        this.alertService = alertService;
    }
    public void analyzeEvent(SecurityEvent event) {

        if (event.getEventType() == EventType.LOGIN_FAILED) {
            checkBruteForce(event);
            checkPasswordSpraying(event);
        }

        if (event.getEventType() == EventType.LOGIN_SUCCESS) {

            checkPossibleAccountCompromise(event);
        }

    }
    private void checkBruteForce(SecurityEvent event) {

        Instant startTime =
                event.getTimestamp().minusSeconds(120);

        long failedAttempts =
                eventRepository
                        .countByEventTypeAndUsernameAndSourceIpAndTimestampAfter(
                                EventType.LOGIN_FAILED,
                                event.getUsername(),
                                event.getSourceIp(),
                                startTime
                        );

        if (failedAttempts >= 5) {
            alertService.createBruteForceAlert(event);
        }
    }

    private void checkPasswordSpraying(SecurityEvent event) {

        Instant startTime =
                event.getTimestamp().minusSeconds(180);

        List<SecurityEvent> recentEvents =
                eventRepository.findByEventTypeAndSourceIpAndTimestampAfter(
                        EventType.LOGIN_FAILED,
                        event.getSourceIp(),
                        startTime
                );

        long differentUsers = recentEvents.stream() // trabalhar com objetos da lista
                .map(SecurityEvent::getUsername)
                .distinct()
                .count();

        if (differentUsers >= 5) {
            alertService.createPasswordSprayingAlert(event);
        }
    }

    private void checkPossibleAccountCompromise(SecurityEvent event) {

        Instant startTime =
                event.getTimestamp().minusSeconds(300);

        long failedAttempts =
                eventRepository
                        .countByEventTypeAndUsernameAndSourceIpAndTimestampAfter(
                                EventType.LOGIN_FAILED,
                                event.getUsername(),
                                event.getSourceIp(),
                                startTime
                        );

        if (failedAttempts >= 5) {

            alertService.createPossibleAccountCompromiseAlert(event);
        }
    }

}



