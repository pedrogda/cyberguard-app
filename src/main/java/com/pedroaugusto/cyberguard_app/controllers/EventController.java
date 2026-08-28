package com.pedroaugusto.cyberguard_app.controllers;

import com.pedroaugusto.cyberguard_app.model.SecurityEvent;
import com.pedroaugusto.cyberguard_app.services.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController (EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping
    public List<SecurityEvent> getAllEvents(){
        return eventService.getAllEvents();
    }

    @PostMapping
    public SecurityEvent createEvent(@RequestBody SecurityEvent event){
        return eventService.createEvent(event);
    }
}
