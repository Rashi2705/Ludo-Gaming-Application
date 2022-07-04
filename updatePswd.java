package com.example.ludo3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Base64;

public class updatePswd extends AppCompatActivity {
    EditText username,password,newpswd;
    TextView gotoupdateuname;
    Button updatepswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pswd);

        username =  findViewById(R.id.uername);
        password = findViewById(R.id.password);
        newpswd = findViewById(R.id.updatepassword);
        updatepswd = findViewById(R.id.button6);
        gotoupdateuname = findViewById(R.id.textView6);

        updatepswd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Database obj = new Database(updatePswd.this); //constructor is called of mydatabase class
                String nameuser = username.getText().toString();
                String pswd = password.getText().toString();
                String newdata = newpswd.getText().toString();
                String encodedpswd = Base64.getEncoder().encodeToString(newdata.getBytes());
                boolean update = obj.searchrec(nameuser,pswd);
                if(update)
                {
                    boolean updatepassw = obj.reset(nameuser, encodedpswd);
                    if(updatepassw)
                    {
                        Toast.makeText(updatePswd.this,"password updated successfully",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(updatePswd.this,MainActivity.class);
                        startActivity(i);
                    }
                    else
                        Toast.makeText(updatePswd.this,"Enter correct details",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(updatePswd.this,"Enter correct details",Toast.LENGTH_SHORT).show();
            }


        });
        gotoupdateuname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(updatePswd.this,Settings.class);
                startActivity(i);
            }
        });
    }
}