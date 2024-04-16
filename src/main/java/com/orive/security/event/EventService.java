package com.orive.security.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EventService {

    private static final Logger logger = Logger.getLogger(EventService.class.getName());

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        logger.log(Level.INFO, "Fetching all events");
        return eventRepository.findAll();
    }

    public Event getEventById(Long eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            logger.log(Level.INFO, "Event found with ID: " + eventId);
            return eventOptional.get();
        } else {
            logger.log(Level.WARNING, "Event not found with ID: " + eventId);
            return null;
        }
    }

    public Event createEvent(Event event) {
        Event savedEvent = eventRepository.save(event);
        logger.log(Level.INFO, "Event created: " + savedEvent.toString());
        return savedEvent;
    }

    public Event updateEvent(Event event) {
        Optional<Event> existingEventOptional = eventRepository.findById(event.getEventId());
        if (existingEventOptional.isPresent()) {
            Event existingEvent = existingEventOptional.get();
            existingEvent.setTitle(event.getTitle());
            existingEvent.setDate(event.getDate());
            Event updatedEvent = eventRepository.save(existingEvent);
            logger.log(Level.INFO, "Event updated: " + updatedEvent.toString());
            return updatedEvent;
        } else {
            logger.log(Level.WARNING, "Event not found with ID: " + event.getEventId());
            return null; // Or throw an exception as per your application's error handling strategy
        }
    }


    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
        logger.log(Level.INFO, "Event deleted with ID: " + eventId);
    }
    
    public Long countAllEvents() {
        return eventRepository.countAllEvents();
    }
}

