package com.pedroaugusto.cyberguard_app.controllers;

import com.pedroaugusto.cyberguard_app.services.SimulatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/simulator")
public class SimulatorController {

        private final SimulatorService simulatorService;

        public SimulatorController(
                SimulatorService simulatorService) {

            this.simulatorService = simulatorService;
        }

        @PostMapping("/brute-force")
        public String simulateBruteForce() {

            simulatorService.simulateBruteForce();

            return "Brute Force simulation completed";
        }

        @PostMapping("/password-spraying")
        public String simulatePasswordSpraying() {

            simulatorService.simulatePasswordSpraying();

            return "Password Spraying simulation completed";
        }

        @PostMapping("/account-compromise")
        public String simulateAccountCompromise() {

            simulatorService.simulateAccountCompromise();

            return "Account Compromise simulation completed";
        }
}

