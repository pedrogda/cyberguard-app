package com.pedroaugusto.cyberguard_app.repository;
import com.pedroaugusto.cyberguard_app.model.EventType;
import  com.pedroaugusto.cyberguard_app.model.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

// Vou trabalhar com SecurityEvent
// Long = save(), findAll(),  findById(), deleteById(), count()
public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Long> {
    
    long countByEventTypeAndUsernameAndSourceIpAndTimestampAfter(
            EventType eventType,
            String username,
            String sourceIp,
            Instant timestamp
    );

    List<SecurityEvent> findByEventTypeAndSourceIpAndTimestampAfter(
            EventType eventType,
            String sourceIp,
            Instant timestamp
    );

    List<SecurityEvent> findAllByOrderByTimestampDesc();

}
