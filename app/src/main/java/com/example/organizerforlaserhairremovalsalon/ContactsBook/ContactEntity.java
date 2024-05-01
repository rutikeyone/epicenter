package com.example.organizerforlaserhairremovalsalon.ContactsBook;

import android.graphics.Bitmap;
import android.net.Uri;

public class ContactEntity {
    private long id;
    private String name;
    private String phone;
    private String comment;
    private Bitmap image;

    public ContactEntity(int id, String name, String phone, String comment, Bitmap image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.comment = comment;
        this.image = image;
    }

    public ContactEntity(String name, String phone, String comment, Bitmap image) {
        this.id = 0L;
        this.name = name;
        this.phone = phone;
        this.comment = comment;
        this.image = image;
    }

    public ContactEntity() {}

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

    public void setImage(Bitmap image) {
        this.image = image;
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

    public Bitmap getImage() { return image; }
}
