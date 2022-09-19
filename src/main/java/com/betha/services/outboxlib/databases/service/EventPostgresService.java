package com.betha.services.outboxlib.databases.service;

import com.betha.services.outboxlib.databases.model.Event;
import com.betha.services.outboxlib.databases.repository.EventPostgresRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EventPostgresService {
    @Autowired
    EventPostgresRepository postgresRepository;

    public List<Event> findAllEventsByStatus(String status) {
        return this.postgresRepository.findByStatus(status);
    }
}
