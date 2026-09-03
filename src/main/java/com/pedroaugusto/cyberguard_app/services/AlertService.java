package com.pedroaugusto.cyberguard_app.services;

import com.pedroaugusto.cyberguard_app.model.*;
import com.pedroaugusto.cyberguard_app.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class AlertService {
    private final AlertRepository alertRepository;

    public AlertService (AlertRepository alertRepository){
        this.alertRepository = alertRepository;
    }

    public void createBruteForceAlert(SecurityEvent event) {

        boolean alertAlreadyExists =
                alertRepository.existsByTypeAndUsernameAndSourceIpAndStatus(
                        AlertType.BRUTE_FORCE,
                        event.getUsername(),
                        event.getSourceIp(),
                        AlertStatus.OPEN
                );

        if (alertAlreadyExists) {
            return;
        }

        Alert alert = new Alert();

        alert.setType(AlertType.BRUTE_FORCE);
        alert.setSeverity(Severity.HIGH);
        alert.setStatus(AlertStatus.OPEN);

        alert.setUsername(event.getUsername());
        alert.setSourceIp(event.getSourceIp());

        alert.setCreatedAt(Instant.now());

        alertRepository.save(alert);
    }
    
    public List<Alert> getAllAlerts() {
        return alertRepository.findAllByOrderByCreatedAtDesc();
    }

    public Alert getAlertById(Long id) {
        return alertRepository.findById(id).orElse(null);
    }

    public Alert updateStatus(Long id, AlertStatus newStatus) {

        Alert alert = alertRepository.findById(id)
                .orElse(null);

        if (alert == null) {
            return null;
        }

        alert.setStatus(newStatus);

        return alertRepository.save(alert);
    }

    public void createPasswordSprayingAlert(SecurityEvent event) {

        boolean alertAlreadyExists =
                alertRepository.existsByTypeAndSourceIpAndStatus(
                        AlertType.PASSWORD_SPRAYING,
                        event.getSourceIp(),
                        AlertStatus.OPEN
                );

        if (alertAlreadyExists) {
            return;
        }

        Alert alert = new Alert();

        alert.setType(AlertType.PASSWORD_SPRAYING);
        alert.setSeverity(Severity.HIGH);
        alert.setStatus(AlertStatus.OPEN);

        alert.setSourceIp(event.getSourceIp());

        alert.setCreatedAt(Instant.now());

        alertRepository.save(alert);
    }

    public void createPossibleAccountCompromiseAlert(
            SecurityEvent event) {

        boolean alertAlreadyExists =
                alertRepository
                        .existsByTypeAndUsernameAndSourceIpAndStatus(
                                AlertType.POSSIBLE_ACCOUNT_COMPROMISE,
                                event.getUsername(),
                                event.getSourceIp(),
                                AlertStatus.OPEN
                        );

        if (alertAlreadyExists) {
            return;
        }

        Alert alert = new Alert();

        alert.setType(
                AlertType.POSSIBLE_ACCOUNT_COMPROMISE
        );

        alert.setSeverity(Severity.CRITICAL);

        alert.setStatus(AlertStatus.OPEN);

        alert.setUsername(event.getUsername());

        alert.setSourceIp(event.getSourceIp());

        alert.setCreatedAt(Instant.now());

        alertRepository.save(alert);
    }

}
