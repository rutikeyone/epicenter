package com.example.organizerforlaserhairremovalsalon.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public abstract class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context) {
        super(context, DatabaseContactsConstants.DATABASE_NAME, null, DatabaseContactsConstants.DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContactsConstants.CREATE_TABLE);
        db.execSQL(DatabaseEventsConstants.CREATE_TABLE);
        db.execSQL(DatabaseServicesConstants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContactsConstants.DROP_TABLE);
        db.execSQL(DatabaseEventsConstants.DROP_TABLE);
        db.execSQL(DatabaseServicesConstants.DROP_TABLE);

        this.onCreate(db);
    }
}
