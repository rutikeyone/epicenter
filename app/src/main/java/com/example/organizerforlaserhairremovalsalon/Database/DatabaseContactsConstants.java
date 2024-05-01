package com.example.organizerforlaserhairremovalsalon.Database;

public class DatabaseContactsConstants extends DatabaseConstants {

    public static final String TABLE_NAME = "CONTACT_TABLE";

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String PHONE = "PHONE";
    public static final String COMMENT = "COMMENT";
    public static final String IMAGE = "IMAGE";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT, "
            + PHONE + " TEXT, "
            + COMMENT + " TEXT, "
            + IMAGE + " BLOB "
            + ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
