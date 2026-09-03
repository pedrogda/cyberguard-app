package com.pedroaugusto.cyberguard_app.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
@Entity // entidade que será salva no banco
@Table(name = "security_events")
public class SecurityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // salva LOGIN_FAILED em vez de 0
    private EventType eventType;

    private String username;

    private String sourceIp;

    private String deviceName;

    private Instant timestamp;
}
