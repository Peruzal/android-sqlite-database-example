package com.example.datapersistenceexampleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.datapersistenceexampleapp.DatabaseSchema.ContactsTable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = DbHelper.class.getCanonicalName() + ".db";
    private static final int DATABASE_VERSION = 1;
    private static MutableLiveData<List<Contact>> contactsStream = new MutableLiveData<>();

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LiveData<List<Contact>> getDataStream() {
        return contactsStream;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseSchema.SQL_CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseSchema.SQL_DELETE_CONTACTS);
        onCreate(db);
    }

    public void saveContact(Contact contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsTable.COLUMN_NAME, contact.name);
        contentValues.put(ContactsTable.COLUMN_EMAIL, contact.email);
        contentValues.put(ContactsTable.COLUMN_PHONE, contact.phone);
        getWritableDatabase().insert(ContactsTable.TABLE_NAME, null, contentValues);
    }

    public void readAllContacts() {
        Thread thread = new Thread(this::readDatabase);
        thread.start();
    }

    private void readDatabase() {
        String[] columns = new String[]{
                ContactsTable.COLUMN_NAME,
                ContactsTable.COLUMN_EMAIL,
                ContactsTable.COLUMN_PHONE};
        String sortOrder = ContactsTable.COLUMN_NAME + " DESC";

        Cursor cursor = getReadableDatabase().query(
                ContactsTable.TABLE_NAME, columns, null, null, null, null, sortOrder);

        List<Contact> contacts = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsTable.COLUMN_NAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(ContactsTable.COLUMN_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsTable.COLUMN_PHONE));
            contacts.add(new Contact(name, email, phone));
        }
        cursor.close();
        contactsStream.postValue(contacts);
    }
}
