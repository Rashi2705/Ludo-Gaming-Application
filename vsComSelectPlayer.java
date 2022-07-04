package com.example.ludo3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class vsComSelectPlayer extends AppCompatActivity {

    Button btnSubmit;
        RadioButton click1;
    TextView plo21;

    String pl1color,pl2color,pl3color,pl4color;
    String pl1n,pl2n,pl3n,pl4n;
    Spinner sp1;
    boolean check=false;
    ArrayList<String> choice=new ArrayList<>();
    ArrayList<String> choice1=new ArrayList<>();
    ArrayList<String>playerNm=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vs_com_select_player);
        plo21=findViewById(R.id.pl1);
        plo21.setText(Home.userName);
        choice.add("Blue");
        choice.add("Green");
        choice.add("Red");
        choice.add("Yellow");
       choice1.add("Blue");
       choice1.add("Green");
        addItemsOnSpinner1();
        btnSubmit=findViewById(R.id.play);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check) {
                    Intent intent = new Intent(vsComSelectPlayer.this, vsComputer.class);
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
                    new AlertDialog.Builder(vsComSelectPlayer.this).setTitle("Please check all the radio button to continue")
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
                android.R.layout.simple_spinner_item,choice1);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);


        click1=findViewById(R.id.radioButton);
        click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
check=true;
                pl1color=sp1.getSelectedItem().toString();
                pl1n=Home.userName;
                playerNm.add(pl1n);
               pl2n="Computer";
               pl2color=choice.get((choice.indexOf(pl1color)+2)%4);

            }
        });

    }
}