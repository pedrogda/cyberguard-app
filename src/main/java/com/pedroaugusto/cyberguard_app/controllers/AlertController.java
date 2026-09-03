package com.pedroaugusto.cyberguard_app.controllers;


import com.pedroaugusto.cyberguard_app.model.Alert;
import com.pedroaugusto.cyberguard_app.services.AlertService;
import dto.AlertStatusRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @GetMapping("/{id}")
    public Alert getAlertById(@PathVariable Long id) {
        return alertService.getAlertById(id);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Alert> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody AlertStatusRequest request) {
        {

            Alert updatedAlert = alertService.updateStatus(
                    id,
                    request.getStatus()
            );

            return ResponseEntity.ok(updatedAlert);
    }
}
}
