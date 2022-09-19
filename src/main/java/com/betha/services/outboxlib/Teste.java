package com.betha.services.outboxlib;

import com.betha.services.outboxlib.databases.model.Event;
import com.betha.services.outboxlib.databases.model.StatusType;
import com.betha.services.outboxlib.databases.service.EventPostgresService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Teste {
    @Autowired
    private static EventPostgresService eventPostgresService;

    public static void main(String[] args) {
        System.out.println("xd");
        List<Event> events = eventPostgresService.findAllEventsByStatus(String.valueOf(StatusType.CREATED));

        System.out.println(events);


    }
}
