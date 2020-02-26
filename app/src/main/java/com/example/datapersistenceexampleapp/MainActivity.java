package com.example.datapersistenceexampleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.EnvironmentCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.editTextName) EditText editTextName;
    @BindView(R.id.editTextEmail) EditText editTextEmail;
    @BindView(R.id.editTextPhone) EditText editTextPhone;
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        ButterKnife.bind(this);
        LiveData<List<Contact>> contactsStream = dbHelper.getDataStream();
        contactsStream.observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                Log.d(MainActivity.class.getSimpleName(), contacts.toString());
            }
        });

    }

    @OnClick(R.id.buttonSave)
    public void saveContact() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String phone = editTextPhone.getText().toString();
        dbHelper.saveContact(new Contact(name, email, phone));
    }

    @OnClick(R.id.buttonRead)
    public void readContacts() {
        dbHelper.readAllContacts();
    }

}
