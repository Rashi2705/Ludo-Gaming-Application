package com.example.ludo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class chooseMode extends AppCompatActivity {
Button human,computer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);
        human=findViewById(R.id.human);
        computer=findViewById(R.id.computer);
        human.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(chooseMode.this,no_of_players.class);
                startActivity(intent);
            }
        });
        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(chooseMode.this,vsComSelectPlayer.class);
                startActivity(intent);
            }
        });
    }
}