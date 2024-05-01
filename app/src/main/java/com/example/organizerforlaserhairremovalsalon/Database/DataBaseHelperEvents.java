package com.example.organizerforlaserhairremovalsalon.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.organizerforlaserhairremovalsalon.Calendar.EventEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DataBaseHelperEvents extends DataBaseHelper {
    public DataBaseHelperEvents(@Nullable Context context) {
        super(context);
    }

    public long addEvent(EventEntity eventEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseEventsConstants.NAME, eventEntity.getName());
        contentValues.put(DatabaseEventsConstants.DATE, eventEntity.getDate().toEpochDay());
        contentValues.put(DatabaseEventsConstants.TIME, eventEntity.getTime().toSecondOfDay());
        contentValues.put(DatabaseEventsConstants.CONTACT_ID, eventEntity.getContactId());
        contentValues.put(DatabaseEventsConstants.SERVICE_ID, eventEntity.getServiceId());

        long id = db.insert(DatabaseEventsConstants.TABLE_NAME, null, contentValues);

        db.close();

        return id;
    }

    public void editEventById(EventEntity eventEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseEventsConstants.NAME, eventEntity.getName());
        contentValues.put(DatabaseEventsConstants.DATE, eventEntity.getDate().toEpochDay());
        contentValues.put(DatabaseEventsConstants.TIME, eventEntity.getTime().toSecondOfDay());
        contentValues.put(DatabaseEventsConstants.CONTACT_ID, eventEntity.getContactId());
        contentValues.put(DatabaseEventsConstants.SERVICE_ID, eventEntity.getServiceId());

        db.update(
                DatabaseEventsConstants.TABLE_NAME,
                contentValues,
                DatabaseEventsConstants.ID + " =? ",
                new String[]{String.valueOf(eventEntity.getId())}
        );

        db.close();
    }

    public void deleteEventById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                DatabaseEventsConstants.TABLE_NAME,
                DatabaseEventsConstants.ID + " =? ",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }

    public ArrayList<EventEntity> getEventsForDay(LocalDate date) {
        ArrayList<EventEntity> result = new ArrayList<>();

        String query = String.format(
                "SELECT * FROM %s WHERE %s = \"%d\" ORDER BY " + DatabaseEventsConstants.TIME,
                DatabaseEventsConstants.TABLE_NAME,
                DatabaseEventsConstants.DATE,
                date.toEpochDay());

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                result.add(
                        new EventEntity(
                                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseEventsConstants.ID)),
                                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseEventsConstants.NAME)),
                                LocalDate.ofEpochDay(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseEventsConstants.DATE))),
                                LocalTime.ofSecondOfDay(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseEventsConstants.TIME))),
                                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseEventsConstants.CONTACT_ID)),
                                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseEventsConstants.SERVICE_ID))
                        )
                );
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return result;
    }

    public void recreateEventsTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(DatabaseEventsConstants.DROP_TABLE);
        sqLiteDatabase.execSQL(DatabaseEventsConstants.CREATE_TABLE);
        sqLiteDatabase.close();
    }
}
