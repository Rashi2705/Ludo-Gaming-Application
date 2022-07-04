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

import java.util.Base64;

public class Register extends AppCompatActivity {
    Button register;
    TextView txtl;
    EditText uname,pswd,cpswd;
    String username,password,cpassword;//for db
    Integer wins , ngame; // for db
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtl = findViewById(R.id.textView2);
        register = findViewById(R.id.button);
        uname = findViewById(R.id.username);
        pswd=findViewById(R.id.pswd);
        cpswd = findViewById(R.id.cpswd);

        Database obj = new Database(this); //constructor is called of mydatabase class
        SQLiteDatabase db = obj.getWritableDatabase(); //for reading db which is created


        txtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this,login.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                username =  uname.getText().toString();
                password=  pswd.getText().toString();

                String encodedpswd = Base64.getEncoder().encodeToString(password.getBytes());
                wins= 0;
                ngame = 0;
                cpassword = cpswd.getText().toString();
                Database obj = new Database(Register.this); //constructor is called of mydatabase class
                SQLiteDatabase db = obj.getReadableDatabase(); //for reading db which is created

                if(!password.equals(cpassword))
                {
                    Toast.makeText(Register.this," password and confirm password not same",Toast.LENGTH_SHORT).show();

                }
                else if(username == null)
                {
                    Toast.makeText(Register.this,"enter username",Toast.LENGTH_SHORT).show();

                }
                else if(password.equals(cpassword) && username!=null)

                {
                    USERMODEL um ;
                    um = new USERMODEL(-1,username,encodedpswd,wins,ngame);
                    Boolean var = obj.insertrec(um);
                    if(var == true)
                        Toast.makeText(Register.this,"Record Inserted",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Register.this,"Record Not Inserted",Toast.LENGTH_SHORT).show();


                }
            }
        });
    }
}