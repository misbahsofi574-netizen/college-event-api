# College Event and Workshop Registration Management REST API

A beginner-friendly REST API built with Java and Spring Boot to manage
college events/workshops and participant registrations.

## Project objectives

-   Create, view, update, and delete college events.
-   Register participants for an event.
-   View registrations for a specific event.
-   Cancel a registration.
-   Validate event and participant details.
-   Return useful HTTP status codes for successful requests and errors.

## Technologies used

-   Java 27
-   Spring Boot 4.1.1
-   Spring Web MVC / REST controllers
-   Maven and Maven Wrapper
-   JSON for request and response data
-   Eclipse IDE (development environment)
-   Git and GitHub 

## How it works

The controller receives HTTP requests and sends them to the service
layer. The service contains the event and registration rules. The model
classes describe the data returned as JSON.

**Storage note:** This project uses in-memory Java maps, not a database.
The application starts with a sample event (ID `1`, "Java Workshop").
Events and registrations created while the application is running are
cleared when the application restarts.

## Project structure

```
college-event-api/
├── src/
│   ├── main/
│   │   ├── java/com/college/event/
│   │   │   ├── CollegeEventApiApplication.java
│   │   │   ├── controller/EventController.java
│   │   │   ├── model/College.java
│   │   │   ├── model/Event.java
│   │   │   ├── model/Registration.java
│   │   │   └── service/EventService.java
│   │   └── resources/application.properties
│   └── test/java/com/college/event/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```


## Requirements

-   JDK 27 (the project `pom.xml` specifies Java 27)
-   A terminal or Eclipse IDE
-   Internet access the first time Maven downloads required dependencies

## Run the application on Windows

1.  Clone or download this repository.

2.  Open PowerShell in the project root (the folder containing
    `pom.xml`).

3.  Run:

    ``` powershell
    .\mvnw.cmd spring-boot:run
    

4.  Wait until Spring Boot reports that the application has started.

5.  Send requests to `http://localhost:8080`.

You can also import the project into Eclipse as an existing Maven
project and run `CollegeEventApiApplication.java`.

## API endpoints

All paths below use the base URL `http://localhost:8080`.

  ------------------------------------------------------------------------------
  Method                  Endpoint                       Purpose
  ----------------------- ------------------------------ -----------------------
  GET                     `/events`                      Get all events

  GET                     `/events/{id}`                 Get one event

  POST                    `/events`                      Create an event

  PUT                     `/events/{id}`                 Update an event

  DELETE                  `/events/{id}`                 Delete an event

  POST                    `/events/{id}/registrations`   Register a participant
                                                         for an event

  GET                     `/events/{id}/registrations`   List registrations for
                                                         an event

  DELETE                  `/registrations/{id}`          Cancel a registration
                                                         by registration ID
  ------------------------------------------------------------------------------

## Example requests

### Get all events

``` http
GET http://localhost:8080/events
```

A sample event is available when the application starts.

### Create an event

``` http
POST http://localhost:8080/events
Content-Type: application/json
```

``` json
{
  "id": 2,
  "title": "AI Workshop",
  "date": "2026-12-10",
  "venue": "Computer Lab",
  "capacity": 30
}
```

The event ID must be included and must not already exist. Use a future
date and a positive capacity.

### Update an event

``` http
PUT http://localhost:8080/events/2
Content-Type: application/json
```

``` json
{
  "title": "Advanced AI Workshop",
  "date": "2026-12-12",
  "venue": "Seminar Hall",
  "capacity": 30
}
```

### Register a participant

``` http
POST http://localhost:8080/events/2/registrations
Content-Type: application/json
```

``` json
{
  "participantName": "Aarav Patil",
  "email": "aarav@example.com"
}
```

Use sample details when testing; do not publish real student personal
information in screenshots.

### View registrations

``` http
GET http://localhost:8080/events/2/registrations
```

### Cancel a registration

``` http
DELETE http://localhost:8080/registrations/1
```

Use the actual registration ID returned by your response.

## Validation and error responses

The service validates event title, date, venue, and capacity. It also
checks participant name and email and prevents registrations beyond the
event capacity.

  -----------------------------------------------------------------------
  Status                              Meaning in this project
  ----------------------------------- -----------------------------------
  `201 Created`                       Event or registration created
                                      successfully

  `200 OK`                            Data returned or event updated
                                      successfully

  `204 No Content`                    Event deleted or registration
                                      cancelled successfully

  `400 Bad Request`                   Invalid event or participant data

  `404 Not Found`                     Requested event or registration
                                      does not exist

  `409 Conflict`                      Duplicate event ID, full event, or
                                      a deletion that conflicts with
                                      existing registrations
  -----------------------------------------------------------------------

## Testing

Test the endpoints using Postman or another HTTP client. Save genuine
screenshots of: - Application startup in the console. - `GET /events`
showing the sample event. - Successful event creation and update. -
Successful participant registration and registration listing. -
Successful deletion/cancellation. - `400 Bad Request`, `404 Not Found`,
and `409 Conflict` examples.

Only report tests as successful if you personally ran them and observed
the response.

## Limitations and future improvements

-   Data is stored in memory and is not permanent.
-   Authentication and role-based access are not included.
-   A future version could use a database, add user authentication, and
    provide a web interface.

## Repository
`GitHub: https://github.com/misbahsofi574-netizen/college-event-api`




