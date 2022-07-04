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

import org.w3c.dom.Text;

public class Settings extends AppCompatActivity {
    EditText username,password,newuname;
    TextView gotoupdatepassword;
    Button updateuname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        username =  findViewById(R.id.uername);
        password = findViewById(R.id.password);
        newuname = findViewById(R.id.updateusername);
        updateuname = findViewById(R.id.button6);
        gotoupdatepassword = findViewById(R.id.textView6);

        updateuname.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Database obj = new Database(Settings.this); //constructor is called of mydatabase class
                String nameuser = username.getText().toString();
                String pswd = password.getText().toString();
                String newdata = newuname.getText().toString();

                boolean update = obj.searchrec(nameuser,pswd);
                if(update)
                {
                    boolean updatename = obj.resetuname(nameuser, newdata);
                    if(updatename)
                    {
                        Toast.makeText(Settings.this,"username updated successfully",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Settings.this,login.class);
                        startActivity(i);
                    }
                    else
                        Toast.makeText(Settings.this,"Enter correct details",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Settings.this,"Enter correct details",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Settings.this,login.class);
                startActivity(i);
            }


        });
        gotoupdatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Settings.this,updatePswd.class);
                startActivity(i);
            }
        });

    }
}