package com.example.ludo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.time.format.TextStyle;

public class Win extends AppCompatActivity {
    Integer win,tplay;
    String username;
    TextView tx, tx1, tx11,tx10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        tx = findViewById(R.id.textView8);
        tx1 = findViewById(R.id.textView9);
        tx10 = findViewById(R.id.textView10);
        tx11 = findViewById(R.id.textView11);
        Database obj = new Database(this); //constructor is called of mydatabase class
        username = Home.userName;
        win = obj.getwin(username);
        System.out.println(win );
        tx.setText(String.valueOf(win));
        tplay = obj.getplay(username);
        System.out.println(tplay);
        tx1.setText(String.valueOf(tplay));




//        System.out.println(tplay1 );
    }
}