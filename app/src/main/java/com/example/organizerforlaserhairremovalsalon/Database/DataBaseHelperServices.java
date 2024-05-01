package com.example.organizerforlaserhairremovalsalon.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.organizerforlaserhairremovalsalon.Services.ServiceEntity;

import java.util.ArrayList;

public class DataBaseHelperServices extends DataBaseHelper {

    public DataBaseHelperServices(@Nullable Context context) {
        super(context);
    }

    public long addService(ServiceEntity service) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseServicesConstants.NAME, service.getName());

        long id = db.insert(DatabaseServicesConstants.TABLE_NAME, null, contentValues);

        db.close();

        return id;
    }

    public void editServiceById(ServiceEntity service) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseServicesConstants.NAME, service.getName());

        db.update(DatabaseServicesConstants.TABLE_NAME,
                contentValues,
                DatabaseServicesConstants.ID + " =? ",
                new String[]{String.valueOf(service.getId())}
        );

        db.close();
    }

    public void deleteServiceById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                DatabaseServicesConstants.TABLE_NAME,
                DatabaseServicesConstants.ID + " =? ",
                new String[]{ String.valueOf(id) }
        );

        db.close();
    }

    public ArrayList<ServiceEntity> getAllServices() {
        ArrayList<ServiceEntity> result = new ArrayList<ServiceEntity>();
        String query = "SELECT * FROM " + DatabaseServicesConstants.TABLE_NAME + " ORDER BY " + DatabaseServicesConstants.NAME;

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                result.add(
                        new ServiceEntity(
                                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseServicesConstants.ID)),
                                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseServicesConstants.NAME))
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return result;
    }

    public ServiceEntity getServiceById(long id) {
        ServiceEntity result = new ServiceEntity();

        String query = String.format(
                "SELECT * FROM %s WHERE %s=\"%d\"",
                DatabaseServicesConstants.TABLE_NAME,
                DatabaseServicesConstants.ID,
                id
        );

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            result.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseServicesConstants.ID)));
            result.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseServicesConstants.NAME)));
        }

        cursor.close();
        sqLiteDatabase.close();

        return result;
    }

    public void recreateServicesTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(DatabaseServicesConstants.DROP_TABLE);
        sqLiteDatabase.execSQL(DatabaseServicesConstants.CREATE_TABLE);
        sqLiteDatabase.close();
    }

}
