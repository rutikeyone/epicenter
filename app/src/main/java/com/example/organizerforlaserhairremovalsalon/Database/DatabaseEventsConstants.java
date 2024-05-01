package com.example.organizerforlaserhairremovalsalon.Database;

public class DatabaseEventsConstants extends DatabaseConstants {
    public static final String TABLE_NAME = "EVENTS_TABLE";

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String DATE = "DATE";
    public static final String TIME = "TIME";
    public static final String CONTACT_ID = "CONTACT_ID";
    public static final String SERVICE_ID = "SERVICE_ID";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CONTACT_ID + " INTEGER, "
            + SERVICE_ID + " INTEGER, "
            + NAME + " TEXT, "
            + DATE + " INTEGER, "
            + TIME + " INTEGER "
            + ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
