
package com.college.event.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.college.event.model.Event;
import com.college.event.model.Registration;

@Service
public class EventService {

    private final Map<Long, Event> events =
            new ConcurrentHashMap<>();

    private final Map<Long, Registration> registrations =
            new ConcurrentHashMap<>();

    private final AtomicLong registrationIdCounter =
            new AtomicLong(1);

    public EventService() {
        events.put(1L, new Event(
                1L,
                "Java Workshop",
                LocalDate.of(2026, 11, 15),
                "College Auditorium",
                2
        ));
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events.values());
    }

    public Event getEvent(Long id) {
        Event event = events.get(id);

        if (event == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Event not found");
        }

        return event;
    }

    public Event createEvent(Event event) {
        validateEvent(event);

        if (event.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Event ID is required");
        }

        if (events.containsKey(event.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Event ID already exists");
        }

        event.setRegisteredCount(0);
        events.put(event.getId(), event);

        return event;
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event existing = getEvent(id);
        validateEvent(updatedEvent);

        if (updatedEvent.getCapacity()
                < existing.getRegisteredCount()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Capacity cannot be less than current registrations");
        }

        existing.setTitle(updatedEvent.getTitle());
        existing.setDate(updatedEvent.getDate());
        existing.setVenue(updatedEvent.getVenue());
        existing.setCapacity(updatedEvent.getCapacity());

        return existing;
    }

    public void deleteEvent(Long id) {
        getEvent(id);

        boolean hasRegistrations = registrations.values()
                .stream()
                .anyMatch(r -> r.getEventId().equals(id));

        if (hasRegistrations) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cancel registrations before deleting this event");
        }

        events.remove(id);
    }

    public Registration registerParticipant(
            Long eventId, Registration registration) {

        Event event = getEvent(eventId);

        if (registration.getParticipantName() == null
                || registration.getParticipantName().isBlank()
                || registration.getEmail() == null
                || registration.getEmail().isBlank()
                || !registration.getEmail().matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Valid participant name and email are required");
        }

        synchronized (event) {
            if (event.getRegisteredCount() >= event.getCapacity()) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Event capacity is full");
            }

            Long id = registrationIdCounter.getAndIncrement();

            registration.setId(id);
            registration.setEventId(eventId);

            registrations.put(id, registration);
            event.setRegisteredCount(
                    event.getRegisteredCount() + 1);

            return registration;
        }
    }

    public List<Registration> getRegistrations(Long eventId) {
        getEvent(eventId);

        return registrations.values()
                .stream()
                .filter(r -> r.getEventId().equals(eventId))
                .toList();
    }

    public void cancelRegistration(Long registrationId) {
        Registration registration =
                registrations.get(registrationId);

        if (registration == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Registration not found");
        }

        Event event = getEvent(registration.getEventId());

        synchronized (event) {
            if (registrations.remove(registrationId) != null) {
                event.setRegisteredCount(
                        event.getRegisteredCount() - 1);
            }
        }
    }

    private void validateEvent(Event event) {
        if (event.getTitle() == null
                || event.getTitle().isBlank()
                || event.getVenue() == null
                || event.getVenue().isBlank()
                || event.getDate() == null
                || event.getDate().isBefore(LocalDate.now())
                || event.getCapacity() <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Title, future date, venue and positive capacity are required");
        }
    }
}
