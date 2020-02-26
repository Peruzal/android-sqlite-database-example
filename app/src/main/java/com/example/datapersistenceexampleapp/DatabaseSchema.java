package com.example.datapersistenceexampleapp;

import android.provider.BaseColumns;

public class DatabaseSchema {
    private DatabaseSchema() {}

    public static class ContactsTable implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE = "phone";
    }

    public static final String SQL_CREATE_CONTACTS_TABLE =
            "CREATE TABLE " + ContactsTable.TABLE_NAME + " ("
            + ContactsTable._ID + " INTEGER PRIMARY KEY, "
            + ContactsTable.COLUMN_NAME + " TEXT, "
            + ContactsTable.COLUMN_EMAIL + " TEXT, "
            + ContactsTable.COLUMN_PHONE + " TEXT)";

    public static final String SQL_DELETE_CONTACTS =
            "DROP TABLE IF EXISTS " + ContactsTable.TABLE_NAME;

}
