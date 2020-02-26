package com.example.datapersistenceexampleapp;

import androidx.annotation.NonNull;

public class Contact {
    public String name;
    public String email;
    public String phone;

    public Contact(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " - " + email + " - " + phone;
    }
}
