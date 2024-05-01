package com.example.organizerforlaserhairremovalsalon.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.organizerforlaserhairremovalsalon.ContactsBook.ContactEntity;

import java.util.ArrayList;

public class DataBaseHelperContacts extends DataBaseHelper {
    public DataBaseHelperContacts(@Nullable Context context) {
        super(context);
    }

    public long addContact(ContactEntity contactEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContactsConstants.NAME, contactEntity.getName());
        contentValues.put(DatabaseContactsConstants.PHONE, contactEntity.getPhone());
        contentValues.put(DatabaseContactsConstants.COMMENT, contactEntity.getComment());

        long id =  db.insert(DatabaseContactsConstants.TABLE_NAME, null, contentValues);

        db.close();

        return id;
    }

    public void editContactById(ContactEntity contactEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContactsConstants.NAME, contactEntity.getName());
        contentValues.put(DatabaseContactsConstants.PHONE, contactEntity.getPhone());
        contentValues.put(DatabaseContactsConstants.COMMENT, contactEntity.getComment());

        db.update(
            DatabaseContactsConstants.TABLE_NAME,
            contentValues,
            DatabaseContactsConstants.ID + " =? ",
            new String[]{String.valueOf(contactEntity.getId())}
        );

        db.close();
    }

    public void deleteContactById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                DatabaseContactsConstants.TABLE_NAME,
                DatabaseContactsConstants.ID + " =? ",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }

    public ArrayList<ContactEntity> getAllContacts() {
        ArrayList<ContactEntity> result = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseContactsConstants.TABLE_NAME + " ORDER BY " + DatabaseContactsConstants.NAME;

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                result.add(
                    new ContactEntity(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.COMMENT))
                    )
                );
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return result;
    }

    public ContactEntity getContactById(long contactId) {
        ContactEntity result = new ContactEntity();

        String query = String.format(
                "SELECT * FROM %s WHERE %s=\"%d\"",
                DatabaseContactsConstants.TABLE_NAME,
                DatabaseContactsConstants.ID,
                contactId);

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            result.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.ID)));
            result.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.NAME)));
            result.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.PHONE)));
            result.setComment(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.COMMENT)));
        }

        sqLiteDatabase.close();

        return result;
    }

    public void recreateContactsTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(DatabaseContactsConstants.DROP_TABLE);
        sqLiteDatabase.execSQL(DatabaseContactsConstants.CREATE_TABLE);
        sqLiteDatabase.close();
    }
}
