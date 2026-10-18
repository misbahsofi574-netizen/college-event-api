
package com.college.event.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.college.event.model.Event;
import com.college.event.model.Registration;
import com.college.event.service.EventService;

@RestController
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // 1. Get all events
    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // 2. Get one event
    @GetMapping("/events/{id}")
    public Event getEvent(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    // 3. Create an event
    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(
            @RequestBody Event event) {
        Event created = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(created);
    }

    // 4. Update an event
    @PutMapping("/events/{id}")
    public Event updateEvent(
            @PathVariable Long id,
            @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    // 5. Delete an event
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Register a participant
    @PostMapping("/events/{id}/registrations")
    public ResponseEntity<Registration> registerParticipant(
            @PathVariable Long id,
            @RequestBody Registration registration) {
        Registration created =
                eventService.registerParticipant(id, registration);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(created);
    }

    // 7. List registrations for an event
    @GetMapping("/events/{id}/registrations")
    public List<Registration> getRegistrations(
            @PathVariable Long id) {
        return eventService.getRegistrations(id);
    }

    // 8. Cancel a registration
    @DeleteMapping("/registrations/{id}")
    public ResponseEntity<Void> cancelRegistration(
            @PathVariable Long id) {
        eventService.cancelRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
