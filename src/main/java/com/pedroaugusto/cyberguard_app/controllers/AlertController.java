package com.pedroaugusto.cyberguard_app.controllers;


import com.pedroaugusto.cyberguard_app.model.Alert;
import com.pedroaugusto.cyberguard_app.services.AlertService;
import dto.AlertStatusRequest;
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
    public Alert updateStatus(
            @PathVariable Long id,
            @RequestBody AlertStatusRequest request) {

        return alertService.updateStatus(
                id,
                request.getStatus()
        );
    }
}
