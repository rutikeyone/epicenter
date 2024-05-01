package com.example.organizerforlaserhairremovalsalon.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.example.organizerforlaserhairremovalsalon.ContactsBook.ContactEntity;
import com.example.organizerforlaserhairremovalsalon.Utils.BitmapUtils;

import java.util.ArrayList;

public class DataBaseHelperContacts extends DataBaseHelper {
    public DataBaseHelperContacts(@Nullable Context context) {
        super(context);
    }

    public long addContact(ContactEntity contactEntity) {
        Bitmap image = contactEntity.getImage();

        byte[] bytes;

        if (image != null) {
            bytes = BitmapUtils.bitmapToByteArray(image);
        } else {
            bytes = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseContactsConstants.NAME, contactEntity.getName());
        contentValues.put(DatabaseContactsConstants.PHONE, contactEntity.getPhone());
        contentValues.put(DatabaseContactsConstants.COMMENT, contactEntity.getComment());
        contentValues.put(DatabaseContactsConstants.IMAGE, bytes);

        long id = db.insert(DatabaseContactsConstants.TABLE_NAME, null, contentValues);

        db.close();

        return id;
    }

    public void editContactById(ContactEntity contactEntity) {
        Bitmap image = contactEntity.getImage();

        byte[] bytes;

        if (image != null) {
            bytes = BitmapUtils.bitmapToByteArray(image);
        } else {
            bytes = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContactsConstants.NAME, contactEntity.getName());
        contentValues.put(DatabaseContactsConstants.PHONE, contactEntity.getPhone());
        contentValues.put(DatabaseContactsConstants.COMMENT, contactEntity.getComment());
        contentValues.put(DatabaseContactsConstants.IMAGE, bytes);

        db.update(
                DatabaseContactsConstants.TABLE_NAME,
                contentValues,
                DatabaseContactsConstants.ID + " =? ",
                new String[]{ String.valueOf(contactEntity.getId()) }
        );

        db.close();
    }

    public void deleteContactById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                DatabaseContactsConstants.TABLE_NAME,
                DatabaseContactsConstants.ID + " =? ",
                new String[]{ String.valueOf(id) }
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
                int imageColumn = cursor.getColumnIndexOrThrow(DatabaseContactsConstants.IMAGE);
                byte[] bytes = cursor.getBlob(imageColumn);

                Bitmap image = bytes != null ? BitmapUtils.byteArrayToBitmap(bytes) : null;

                result.add(
                        new ContactEntity(
                                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.ID)),
                                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.NAME)),
                                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.PHONE)),
                                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.COMMENT)),
                                image)
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return result;
    }

    public ContactEntity getContactById(long contactId) {
        ContactEntity result = new ContactEntity();

        String query = String.format(
                "SELECT * FROM %s WHERE %s=\"%d\"",
                DatabaseContactsConstants.TABLE_NAME,
                DatabaseContactsConstants.ID,
                contactId
        );

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int imageColumn = cursor.getColumnIndexOrThrow(DatabaseContactsConstants.IMAGE);
            byte[] bytes = cursor.getBlob(imageColumn);

            Bitmap image = bytes != null ? BitmapUtils.byteArrayToBitmap(bytes) : null;

            result.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.ID)));
            result.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.NAME)));
            result.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.PHONE)));
            result.setComment(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.COMMENT)));
            result.setImage(image);
        }

        cursor.close();
        sqLiteDatabase.close();

        return result;
    }

    public ArrayList<ContactEntity> searchContactsByName(String searchName) {
        ArrayList<ContactEntity> result = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String tableName = DatabaseContactsConstants.TABLE_NAME;

        String[] columns = {
                DatabaseContactsConstants.ID,
                DatabaseContactsConstants.NAME,
                DatabaseContactsConstants.PHONE,
                DatabaseContactsConstants.COMMENT,
                DatabaseContactsConstants.IMAGE,
        };

        String selection = DatabaseContactsConstants.NAME + " LIKE ?";

        String[] selectionArgs = new String[]{ "%"+ searchName+ "%" };

        Cursor cursor = sqLiteDatabase.query(
                tableName,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int imageColumn = cursor.getColumnIndexOrThrow(DatabaseContactsConstants.IMAGE);
                byte[] bytes = cursor.getBlob(imageColumn);

                Bitmap image = bytes != null ? BitmapUtils.byteArrayToBitmap(bytes) : null;

                result.add(
                        new ContactEntity(
                                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.ID)),
                                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.NAME)),
                                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.PHONE)),
                                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContactsConstants.COMMENT)),
                                image)
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
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
