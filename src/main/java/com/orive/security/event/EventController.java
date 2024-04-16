package com.orive.security.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/events")
@Tag(name = "Event")
@CrossOrigin(origins = "*")
public class EventController {

    private static final Logger logger = Logger.getLogger(EventController.class.getName());

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        logger.log(Level.INFO, "Fetched all events");
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        Event event = eventService.getEventById(eventId);
        if (event != null) {
            logger.log(Level.INFO, "Event found with ID: " + eventId);
            return ResponseEntity.ok(event);
        } else {
            logger.log(Level.WARNING, "Event not found with ID: " + eventId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        logger.log(Level.INFO, "Event created: " + createdEvent.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId, @RequestBody Event event) {
        event.setEventId(eventId);
        Event updatedEvent = eventService.updateEvent(event);
        logger.log(Level.INFO, "Event updated: " + updatedEvent.toString());
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        logger.log(Level.INFO, "Event deleted with ID: " + eventId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> countAllEvents() {
        Long eventCount = eventService.countAllEvents();
        return ResponseEntity.ok(eventCount);
    }
}

