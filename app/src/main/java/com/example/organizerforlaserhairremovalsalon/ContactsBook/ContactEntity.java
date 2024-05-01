package com.example.organizerforlaserhairremovalsalon.ContactsBook;

public class ContactEntity {
    private long id;
    private String name;
    private String phone;
    private String comment;

    public ContactEntity(int id, String name, String phone, String comment) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.comment = comment;
    }

    public ContactEntity(String name, String phone, String comment) {
        this.id = 0L;
        this.name = name;
        this.phone = phone;
        this.comment = comment;
    }

    public ContactEntity() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getComment() {
        return comment;
    }
}
