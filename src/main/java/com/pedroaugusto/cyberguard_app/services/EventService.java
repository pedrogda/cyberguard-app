package com.pedroaugusto.cyberguard_app.services;


import com.pedroaugusto.cyberguard_app.model.SecurityEvent;
import com.pedroaugusto.cyberguard_app.repository.SecurityEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

   private final SecurityEventRepository eventRepository;
   private final DetectionService detectionService;

   public EventService(SecurityEventRepository eventRepository, DetectionService detectionService ){
      this.eventRepository = eventRepository;
      this.detectionService = detectionService;
   }

   public List<SecurityEvent> getAllEvents() {
      return eventRepository.findAll();
   }

   public SecurityEvent createEvent(SecurityEvent event) {

      event.setTimestamp(LocalDateTime.now());

      SecurityEvent savedEvent = eventRepository.save(event);

      detectionService.analyzeEvent(savedEvent);

      return savedEvent;
   }
}
