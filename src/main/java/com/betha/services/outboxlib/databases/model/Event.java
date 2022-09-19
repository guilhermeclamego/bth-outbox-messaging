package com.betha.services.outboxlib.databases.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(schema = "OUTBOX_DRIVEN", name = "EVENT")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_SEQUENCE_ID")
    @Column(name = "ID")
    private long id;

    @NotNull
    @Column(name = "AGGREGATE_TYPE")
    private String aggregateType;

    @NotNull
    @Column(name = "AGGREGATE_ID")
    private String aggregateId;

    @NotNull
    @Column(name = "AGGREGATE_TYPE")
    private String eventType;

    @NotNull
    @Column(name = "STATUS")
    private StatusType status;

    @NotNull
    @Column(name = "PAYLOAD")
    private String payload;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", aggregateType='" + aggregateType + '\'' +
                ", aggregateId='" + aggregateId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", status=" + status +
                ", payload='" + payload + '\'' +
                '}';
    }
}
