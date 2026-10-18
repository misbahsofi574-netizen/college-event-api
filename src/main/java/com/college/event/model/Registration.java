
package com.college.event.model;

public class Registration {

    private Long id;
    private Long eventId;
    private String participantName;
    private String email;

    public Registration() {
    }

    public Registration(Long id, Long eventId,
                        String participantName, String email) {
        this.id = id;
        this.eventId = eventId;
        this.participantName = participantName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
