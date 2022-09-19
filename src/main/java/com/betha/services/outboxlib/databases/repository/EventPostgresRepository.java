package com.betha.services.outboxlib.databases.repository;

import com.betha.services.outboxlib.databases.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventPostgresRepository extends CrudRepository<Event, Long> {

    @Query(value = "SELECT e.* FROM outbox_driven.event e WHERE status e.status like :status", nativeQuery = true)
    List<Event> findByStatus(@Param("status") String status);
}
