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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SpinnerActivity extends AppCompatActivity {
    private Spinner  spinner1,spinner2,spinner3,spinner4;
    private Button btnSubmit;
    RadioButton ok1,ok2,ok3,ok4;
    TextView pl1;
    ArrayList<String> playersName=new ArrayList<>();
    EditText pl2,pl3,pl4;
    boolean check1=false,check2=false,check3=false,check4=false;
String pl1Choice,pl2Choice,pl3Choice,pl4Choice;
String pl1Name,pl2Name,pl3Name,pl4Name;
    ArrayList<String>colorChoice=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        pl1=findViewById(R.id.player1);
        pl1.setText(Home.userName);
        colorChoice.add("Blue");
        colorChoice.add("Green");
        colorChoice.add("Red");
        colorChoice.add("Yellow");

        addItemsOnSpinner1();
        btnSubmit=findViewById(R.id.btnsubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check3 && check2 && check1 && check4) {
                    Intent intent = new Intent(SpinnerActivity.this, MainActivity.class);


                    intent.putExtra("Pl1Name", pl1Name);
                    intent.putExtra("Pl2Name", pl2Name);
                    intent.putExtra("Pl3Name", pl3Name);
                    intent.putExtra("Pl4Name", pl4Name);
                    intent.putExtra("Pl1Choice", pl1Choice);
                    intent.putExtra("Pl2Choice", pl2Choice);
                    intent.putExtra("Pl3Choice", pl3Choice);
                    intent.putExtra("Pl4Choice", pl4Choice);
                    startActivity(intent);
                }
                else{
                    new AlertDialog.Builder(SpinnerActivity.this).setTitle("Please check all the radio button to continue")
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

        spinner1 = (Spinner) findViewById(R.id.spinner1);


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,colorChoice);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);


        ok1=findViewById(R.id.rdbtn1);
        ok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
check1=true;
                pl1Choice=spinner1.getSelectedItem().toString();
                pl1Name=Home.userName;
playersName.add(pl1Name);
                colorChoice.remove(pl1Choice);
                addItemsOnSpinner2();
            }
        });

    }
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        pl2=findViewById(R.id.player2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,colorChoice);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        ok2=findViewById(R.id.rdbtn2);
        ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check2=true;
if(playersName.contains(pl2.getText().toString())){
    new AlertDialog.Builder(SpinnerActivity.this).setTitle("Please enter a unique user id")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }).show();
    pl2.getText().clear();

}
                pl2Name=pl2.getText().toString();
                pl2Choice=spinner2.getSelectedItem().toString();

                colorChoice.remove(pl2Choice);
                addItemsOnSpinner3();
            }
        });

    }
    public void addItemsOnSpinner3() {

        spinner3 = (Spinner) findViewById(R.id.spinner3);
        pl3=findViewById(R.id.player3);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,colorChoice);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);
        ok3=findViewById(R.id.rdbtn3);
        ok3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check3=true;
                pl3Choice=spinner3.getSelectedItem().toString();
                pl3Name=pl3.getText().toString();
                colorChoice.remove(pl3Choice);
                addItemsOnSpinner4();
            }
        });

    }
    public void addItemsOnSpinner4() {

        spinner4 = (Spinner) findViewById(R.id.spinner4);
        pl4=findViewById(R.id.player4);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,colorChoice);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter);
        ok4=findViewById(R.id.rdbtn4);
        ok4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check4=true;
                pl4Choice=spinner4.getSelectedItem().toString();
                pl4Name=pl4.getText().toString();



            }
        });

    }


    // get the selected dropdown list value
//    public void addListenerOnButton() {
//
//       // spinner1 = (Spinner) findViewById(R.id.spinner1);
//        spinner1 = (Spinner) findViewById(R.id.spinner1);
//        btnSubmit = (Button) findViewById(R.id.btnSubmit);
//
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(SpinnerActivity.this,
//                        "OnClickListener : " +
//                               // "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
//                                "\nSpinner 2 : "+ String.valueOf(spinner1.getSelectedItem()),
//                        Toast.LENGTH_SHORT).show();
//            }
//
//        });
//    }
}
