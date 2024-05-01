package com.example.organizerforlaserhairremovalsalon.Database;

public class DatabaseServicesConstants extends DatabaseConstants {

    public static final String TABLE_NAME = "SERVICES_TABLE";

    public static final String ID = "ID";
    public static final String NAME = "NAME";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT "
            + ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
