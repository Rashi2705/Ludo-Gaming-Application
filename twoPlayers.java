package com.example.ludo3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class twoPlayers extends AppCompatActivity {
Button btnSubmit;
RadioButton rdb1,rdb2;
TextView plo21;
EditText plo22,plo23,plo24;
String pl1color,pl2color,pl3color,pl4color;
String pl1n,pl2n,pl3n,pl4n;
Spinner sp1,sp2;
boolean check1=false,check2=false;
ArrayList<String> choice=new ArrayList<>();
ArrayList<String>playerNm=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players);
        plo21=findViewById(R.id.pl1);
        plo21.setText(Home.userName);

        choice.add("Blue");
        choice.add("Green");
        choice.add("Red");
        choice.add("Yellow");
        addItemsOnSpinner1();


        btnSubmit=findViewById(R.id.btn2option);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check1 && check2) {
                    Intent intent = new Intent(twoPlayers.this, MainActivity.class);
                    intent.putExtra("Pl1Name", pl1n);
                    intent.putExtra("Pl2Name", pl2n);
                    intent.putExtra("Pl3Name", pl3n);
                    intent.putExtra("Pl4Name", pl4n);
                    intent.putExtra("Pl1Choice", pl1color);
                    intent.putExtra("Pl2Choice", pl2color);
                    intent.putExtra("Pl3Choice", pl3color);
                    intent.putExtra("Pl4Choice", pl4color);
                    startActivity(intent);
                }
                else{
                    new AlertDialog.Builder(twoPlayers.this).setTitle("Please check all the radio button to continue")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            }

        });

    }
    public void addItemsOnSpinner1() {

        sp1 = (Spinner) findViewById(R.id.spinner1);


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,choice);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);

        rdb1=findViewById(R.id.radiobtn1);
        rdb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
check1=true;
                pl1color=sp1.getSelectedItem().toString();
                pl1n=plo21.getText().toString();
                playerNm.add(pl1n);
                choice.remove(pl1color);
                addItemsOnSpinner2();
            }
        });





    }
    public void addItemsOnSpinner2() {

        sp2 = (Spinner) findViewById(R.id.spinner2);
        plo22=findViewById(R.id.pl2);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,choice);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter);




        rdb2=findViewById(R.id.radioBtn2);
        rdb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(playerNm.contains(plo22.getText().toString())){
                    new AlertDialog.Builder(twoPlayers.this).setTitle("Please enter a unique user id")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                    plo22.getText().clear();

                }
                pl2n=plo22.getText().toString();
                pl2color=sp2.getSelectedItem().toString();
check2=true;
            }
        });

    }

}