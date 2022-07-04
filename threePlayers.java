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

public class threePlayers extends AppCompatActivity {
    Button btnCont;
    RadioButton btn1,btn2,btn3;
    TextView pl31;
    EditText pl32,pl33,pl34;
    String pl31color,pl32color,pl33color,pl34color;
    String pl1nm,pl2nm,pl3nm,pl4nm;
    Spinner spn1,spn2,spn3;
    boolean check1=false,check2=false,check3=false;
    ArrayList<String> choiceC=new ArrayList<>();
    ArrayList<String>player3Nm=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_players);
        pl31=findViewById(R.id.pl1name);
        pl31.setText(Home.userName);
        choiceC.add("Blue");
        choiceC.add("Green");
        choiceC.add("Red");
        choiceC.add("Yellow");
        addItemsOnSpinner1();
        btnCont=findViewById(R.id.btnoption3);
        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check1 && check2 && check3) {
                    Intent intent = new Intent(threePlayers.this, MainActivity.class);
                    intent.putExtra("Pl1Name", pl1nm);
                    intent.putExtra("Pl2Name", pl2nm);
                    intent.putExtra("Pl3Name", pl3nm);
                    intent.putExtra("Pl4Name", pl4nm);
                    intent.putExtra("Pl1Choice", pl31color);
                    intent.putExtra("Pl2Choice", pl32color);
                    intent.putExtra("Pl3Choice", pl33color);
                    intent.putExtra("Pl4Choice", pl34color);
                    startActivity(intent);
                }
                else{
                    new AlertDialog.Builder(threePlayers.this).setTitle("Please check all the radio button to continue")
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

        spn1 = (Spinner) findViewById(R.id.spinner1);


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,choiceC);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn1.setAdapter(adapter);


        btn1=findViewById(R.id.radio1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
check1=true;
                pl31color=spn1.getSelectedItem().toString();
                pl1nm=Home.userName;
                player3Nm.add(pl1nm);
                choiceC.remove(pl31color);
                addItemsOnSpinner2();
            }
        });

    }
    public void addItemsOnSpinner2() {

        spn2 = (Spinner) findViewById(R.id.spinner2);
        pl32=findViewById(R.id.pl2name);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,choiceC);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn2.setAdapter(adapter);


        btn2=findViewById(R.id.radio2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
check2=true;
                if(player3Nm.contains(pl32.getText().toString())){
                    new AlertDialog.Builder(threePlayers.this).setTitle("Please enter a unique user id")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                    pl32.getText().clear();

                }
                pl2nm=pl32.getText().toString();
                pl32color=spn2.getSelectedItem().toString();
                choiceC.remove(pl32color);
                addItemsOnSpinner3();
            }
        });
    }
    public void addItemsOnSpinner3() {

        spn3 = (Spinner) findViewById(R.id.spinner3);
        pl33=findViewById(R.id.pl3name);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,choiceC);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn3.setAdapter(adapter);


        btn3=findViewById(R.id.radio3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
check3=true;
                if(player3Nm.contains(pl33.getText().toString())){
                    new AlertDialog.Builder(threePlayers.this).setTitle("Please enter a unique user id")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                    pl33.getText().clear();

                }
                pl3nm=pl33.getText().toString();
                pl33color=spn3.getSelectedItem().toString();

            }
        });
    }
}