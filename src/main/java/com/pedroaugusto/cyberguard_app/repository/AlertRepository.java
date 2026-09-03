package com.pedroaugusto.cyberguard_app.repository;

import com.pedroaugusto.cyberguard_app.model.Alert;
import com.pedroaugusto.cyberguard_app.model.AlertStatus;
import com.pedroaugusto.cyberguard_app.model.AlertType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    boolean existsByTypeAndUsernameAndSourceIpAndStatus(
            AlertType type,
            String username,
            String sourceIp,
            AlertStatus status
    );

    boolean existsByTypeAndSourceIpAndStatus(
            AlertType type,
            String sourceIp,
            AlertStatus status
    );
    List<Alert> findAllByOrderByCreatedAtDesc();
}
