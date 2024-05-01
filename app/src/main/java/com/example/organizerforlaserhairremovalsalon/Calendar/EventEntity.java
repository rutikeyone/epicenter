package com.example.organizerforlaserhairremovalsalon.Calendar;

import com.example.organizerforlaserhairremovalsalon.ContactsBook.ContactEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EventEntity {
    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    private long contactId;

    private ContactEntity contactEntity;

    public static ArrayList<EventEntity> eventsArrayList = new ArrayList<>();

    public static ArrayList<EventEntity> eventsForDate(LocalDate date) {
        ArrayList<EventEntity> result = new ArrayList<>();

        for(EventEntity eventEntity : EventEntity.eventsArrayList) {
            if (eventEntity.getDate().equals(date)) {
                result.add(eventEntity);
            }
        }

        return result;
    }

    public EventEntity(long id, String name, LocalDate date, LocalTime time, long contactId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.contactId = contactId;
    }

    public EventEntity(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContactId() {
        return this.contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public void setContactEntity(ContactEntity contactEntity) {
        this.contactEntity = contactEntity;
    }

    public ContactEntity getContactEntity() {
        return contactEntity;
    }
}
