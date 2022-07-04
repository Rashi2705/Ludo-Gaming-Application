package com.example.ludo3;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    TextView txlinkreg,txlinkForgot;
   Button btnLogin;
    EditText username,pswd;
    String name,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txlinkreg = findViewById(R.id.textView2);
        btnLogin= findViewById(R.id.button);
        if(findViewById(R.id.button)==null) System.out.println("yes");

        username =findViewById(R.id.username);
        pswd = findViewById(R.id.pswd);
        txlinkForgot = findViewById(R.id.textView3);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Database obj = new Database(login.this); //constructor is called of mydatabase class
                SQLiteDatabase db = obj.getReadableDatabase(); //for reading db which is created
                String name = username.getText().toString();
                String password = pswd.getText().toString();



                boolean checkvalidt = obj.searchrec(name,password);

                if(checkvalidt) {
                    Intent i = new Intent(login.this, Home.class);
                    i.putExtra("uname", name);
                    startActivity(i);
                    finish();
                }
                else
                    Toast.makeText(login.this,"Enter correct details",Toast.LENGTH_SHORT).show();


            }
        });
        txlinkreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this,Register.class);
                startActivity(i);
            }
        });
        txlinkForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this,ForgotPassword.class);

                startActivity(i);
            }
        });


    }
    public void onBackPress(){
        return;
    }
}