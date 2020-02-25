package com.example.datapersistenceexampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.editTextName) EditText editTextName;
    @BindView(R.id.editTextEmail) EditText editTextEmail;
    @BindView(R.id.editTextPhone) EditText editTextPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.buttonSave)
    public void saveContact() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String phone = editTextPhone.getText().toString();


    }

}
