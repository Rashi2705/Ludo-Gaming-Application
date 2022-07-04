package com.example.ludo3;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    TextView welcome;
    Button win,play,setting,logout;
    String dataname;
    static String userName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcome= findViewById(R.id.textView);

        play = findViewById(R.id.button2);
        win = findViewById(R.id.btnn1);
        win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Win.class);
                startActivity(i);
            }
        });
        setting = findViewById(R.id.button3);
        logout = findViewById(R.id.button4);
        Intent i = getIntent();
        dataname = i.getStringExtra("uname");
        if(dataname==null)
            dataname=i.getStringExtra("username");
        welcome.setText("Welcome "+" "+ dataname);
        userName = dataname;
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Settings.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Intent i=new Intent(Home.this,login.class);
startActivity(i);
finish();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,chooseMode.class);
                startActivity(i);
            }

        });



    }
    public void onBackPress(){
     return;
    }
}