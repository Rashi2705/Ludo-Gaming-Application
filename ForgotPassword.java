package com.example.ludo3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Base64;

public class ForgotPassword extends AppCompatActivity {
    EditText username,newpassword;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        username = findViewById(R.id.resetuname);
        newpassword =findViewById(R.id.newpswd);
        reset=findViewById(R.id.button5);
        reset.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Database obj = new Database(ForgotPassword.this); //constructor is called of mydatabase class
                String nameuser = username.getText().toString();
                String newpswd = newpassword.getText().toString();
                String encodedpswd = Base64.getEncoder().encodeToString(newpswd.getBytes());

                SQLiteDatabase db = obj.getWritableDatabase(); //for reading db which is created
                boolean update = obj.reset(nameuser,encodedpswd);
                if(update)
                {
                    Toast.makeText(ForgotPassword.this,"password updated successfully",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ForgotPassword.this,"Enter correct details",Toast.LENGTH_SHORT).show();
            }
        });


    }
}