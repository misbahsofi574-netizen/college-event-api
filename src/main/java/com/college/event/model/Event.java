
package com.college.event.model;

import java.time.LocalDate;

public class Event {

    private Long id;
    private String title;
    private LocalDate date;
    private String venue;
    private int capacity;
    private int registeredCount;

    public Event() {
    }

    public Event(Long id, String title, LocalDate date,
                 String venue, int capacity) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.venue = venue;
        this.capacity = capacity;
        this.registeredCount = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRegisteredCount() {
        return registeredCount;
    }

    public void setRegisteredCount(int registeredCount) {
        this.registeredCount = registeredCount;
    }
}
