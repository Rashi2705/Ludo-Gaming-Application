package com.example.ludo3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class vsComputer extends AppCompatActivity {



        ImageView imgblue ,imgred,imgyel,imggreen ;
        int dice[] = {R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3,
                R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6,R.drawable.blank_dice};
        ArrayList<FrameLayout> bluePath=new ArrayList<>();
        ArrayList<FrameLayout> greenPath=new ArrayList<>();
        ArrayList<FrameLayout> redPath=new ArrayList<>();
        ArrayList<FrameLayout> yellowPath=new ArrayList<>();
        ArrayList<Boolean>isBlueHome=new ArrayList<Boolean>();
        ArrayList<Boolean>isGreenHome=new ArrayList<Boolean>();
        ArrayList<Boolean>isRedHome=new ArrayList<Boolean>();
        ArrayList<Boolean>isYellowHome=new ArrayList<Boolean>();
        ArrayList<FrameLayout>iniSafePos=new ArrayList<>();
        ImageView Dice;
        int dice_output;
        public View v;
        int turn=1;

        ImageView colorDice;
boolean humanturn=true;
        LinearLayout.LayoutParams p;
        //...........map for mapping player with colour and dice.........
        HashMap<String,ImageView> diceColour=new HashMap<>();
        HashMap<String,String>playerColour=new HashMap<>();
        HashMap<ImageView,String>playerDice=new HashMap<>();
        HashMap<ImageView, TextView> diceText=new HashMap<>();
        HashMap<ImageView,ArrayList> tokenColor=new HashMap<>();
        ArrayList<ImageView>diceSeq=new ArrayList<>();
        String pl1Name, pl2Name,pl3Name,pl4Name;
        String pl1Color,pl2Color,pl3Color,pl4Color;

        //....Token image view list................
        ArrayList<ImageView>blueToken=new ArrayList<>();
        ArrayList<ImageView>blueToken3=new ArrayList<>();
        ArrayList<ImageView>greenToken=new ArrayList<>();
        ArrayList<ImageView>greenToken3=new ArrayList<>();
        ArrayList<ImageView>redToken=new ArrayList<>();
        ArrayList<ImageView>yellowToken=new ArrayList<>();
        ArrayList<ImageView>yellowToken3=new ArrayList<>();
        ArrayList<ImageView>redToken3=new ArrayList<>();

        //.............token Home position list....................
        ArrayList<FrameLayout> blueHome=new ArrayList<>();
        ArrayList<FrameLayout> greenHome=new ArrayList<>();
        ArrayList<FrameLayout> redHome=new ArrayList<>();
        ArrayList<FrameLayout> yellowHome=new ArrayList<>();
        int blueWin=0,redWin=0,yellowWin=0,greenWin=0;

        HashMap<View,FrameLayout> tokenPos=new HashMap<>();
        HashMap<FrameLayout,View> tokenkill=new HashMap<>(); //4april
        ArrayList<FrameLayout> safepoints = new ArrayList<>(); //4april
//.............testing..................



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            imgblue = findViewById(R.id.imageView3);
            imgyel = findViewById(R.id.imageView);
            imggreen = findViewById(R.id.imageView4);
            imgred = findViewById(R.id.imageView2);

            imgblue.setImageResource(dice[1]);
            imgyel.setImageResource(dice[1]);
            imggreen.setImageResource(dice[1]);
            imgred.setImageResource(dice[1]);




            new setToPosition().execute();
            new setPath().execute();
            mapPlayerToDice();
            setDiceVisibility();

        }


        public void rolle(View v)
        {
humanturn=true;
            Random random = new Random();
            Dice=(ImageView) v;
            colorDice=(ImageView) v;
            int diceNum = random.nextInt(6);

            dice_output=diceNum+1;

            Set<View> Key=tokenPos.keySet();

            if(v == imgblue) {

                if(playerDice.get(colorDice).equals("Computer"))
                    System.out.println("com  "+dice_output);
                else {
                    System.out.println("human  " + dice_output);
                    humanturn=false;
                }
                imgblue.setImageResource(dice[diceNum]);
                boolean checkb=false;
                for(View vi:Key){
                    if(blueToken.contains(vi)&&(safepoints.contains(tokenPos.get(vi))||iniSafePos.contains(tokenPos.get(vi)))){
                        ViewGroup vgrp=(ViewGroup) vi.getParent();
                        vgrp.removeView(vi);
                        vgrp.addView(vi);
                    }
                }

                int freq = Collections.frequency(isBlueHome,true);
                if(freq+blueWin==4&&blueWin!=0) {
                    incrementChance();
                    checkb=true;
                }
                //if all in home

                else if(!isBlueHome.contains(false)&& dice_output!=6){
                    incrementChance();
                    checkb=true;
                }


                else if(!isBlueHome.contains(false)&& dice_output==6) {
                    Random random2=new Random();
                    ImageView imgB=blueToken.get(random2.nextInt(4));
                    FrameLayout resFrame=findViewById(R.id.if2);
                    ViewGroup parent=(ViewGroup) imgB.getParent();
                    parent.removeView(imgB);
                    resFrame.addView(imgB);
                    tokenPos.put(imgB,resFrame);
                    isBlueHome.set(blueToken.indexOf(imgB),false);
                    checkb=true;
                    if(playerDice.get(imgblue).equals("Computer"))
                        rolle(colorDice);

                }



                else if( dice_output!=6 && dice_output!=0)
                {

                    if (freq == 3) {
                        ImageView imb = blueToken.get(isBlueHome.indexOf(false));
                        autoChange(imb);
                        checkb=true;

                    }
                    //2 at home 1 out and no six
                    else if (freq == 2) {

                        ImageView imb1 = blueToken.get(isBlueHome.indexOf(false));
                        ImageView imb2 = blueToken.get(isBlueHome.lastIndexOf(false));

                        FrameLayout prevPos = tokenPos.get(imb1);
                        int count = bluePath.indexOf(prevPos);
                        int index = count + dice_output;

                        FrameLayout prevPos1 = tokenPos.get(imb2);
                        int count1 = bluePath.indexOf(prevPos1);
                        int index1 = count1 + dice_output;


                        if (index <= bluePath.size() && index1 > bluePath.size())
                        {
                            autoChange(imb1);
                            checkb=true;
                        }
                        else if (index1 <= bluePath.size() && index > bluePath.size())
                        {
                            autoChange(imb2);
                            checkb=true;
                        }

                    }
                    // 1 at home 3 out and no six
                    else if (freq == 1) {

                        int count, count1, count2;
                        int index, index1, index2;
                        ImageView ntrue = blueToken.get(isBlueHome.indexOf(true));
                        for (int i = 0; i < blueToken.size(); i++)
                        {
                            ImageView imb = blueToken.get(i);
                            if (ntrue != imb)
                            {
                                blueToken3.add(blueToken.get(i));
                            }

                        }
                        ImageView imb1 = blueToken3.get(0);
                        ImageView imb2 = blueToken3.get(1);
                        ImageView imb3 = blueToken3.get(2);

                        FrameLayout prevPos = tokenPos.get(imb1);
                        count = bluePath.indexOf(prevPos);
                        index = count + dice_output;

                        FrameLayout prevPos1 = tokenPos.get(imb2);
                        count1 = bluePath.indexOf(prevPos1);
                        index1 = count1 + dice_output;

                        FrameLayout prevPos2 = tokenPos.get(imb3);
                        count2 = bluePath.indexOf(prevPos2);
                        index2 = count2 + dice_output;


                        if (index <= bluePath.size() && index1 > bluePath.size() && index2 > bluePath.size()) {
                            autoChange(imb1);
                            checkb=true;

                        } else if (index1 <= bluePath.size() && index > bluePath.size() && index2 > bluePath.size()) {
                            autoChange(imb2);
                            checkb=true;

                        } else if (index2 <= bluePath.size() && index1 > bluePath.size() && index > bluePath.size()) {
                            autoChange(imb3);
                            checkb=true;

                        }

                    }
                }
                // all out means dice value can be anything
                else if(freq == 0 && dice_output!=0)
                {

                    int index,index1,index2,index3;
                    int count,count1,count2,count3;
                    ImageView imb1 = blueToken3.get(0);
                    ImageView imb2 = blueToken3.get(1);
                    ImageView imb3 = blueToken3.get(2);
                    ImageView imb4 = blueToken3.get(3);


                    FrameLayout prevPos = tokenPos.get(imb1);
                    count = bluePath.indexOf(prevPos);
                    index = count+dice_output;

                    FrameLayout prevPos1 = tokenPos.get(imb2);
                    count1 = bluePath.indexOf(prevPos1);
                    index1 = count1+dice_output;

                    FrameLayout prevPos2 = tokenPos.get(imb3);
                    count2 = bluePath.indexOf(prevPos2);
                    index2 = count2+dice_output;

                    FrameLayout prevPos3 = tokenPos.get(imb4);
                    count3 = bluePath.indexOf(prevPos3);
                    index3 = count3+dice_output;


                    if(index <= bluePath.size() && index1 > bluePath.size()&& index2 > bluePath.size() && index3 > bluePath.size())
                    {
                        autoChange(imb1);
                        checkb=true;
                    }
                    else if(index1 <=bluePath.size() && index > bluePath.size()&& index2 > bluePath.size()&& index3 > bluePath.size())
                    {
                        autoChange(imb2);
                        checkb=true;
                    }
                    else if(index2 <=bluePath.size() && index1 > bluePath.size() && index > bluePath.size()&& index3 > bluePath.size())
                    {
                        autoChange(imb3);
                        checkb=true;
                    }
                    else if(index3 <=bluePath.size() && index1 > bluePath.size() && index > bluePath.size()&& index2 > bluePath.size())
                    {
                        autoChange(imb4);
                        checkb=true;
                    }
                }
                if(!checkb){
                    colorDice.setClickable(false);

                    int k=0;
                    FrameLayout blueW=findViewById(R.id.ig7);
                    for( k=0;k<4;k++){
                        if(bluePath.indexOf(tokenPos.get(blueToken.get(k)))+dice_output>56||isBlueHome.get(k)==true||tokenPos.get(blueToken.get(k))==blueW)
                            continue;
                        else
                            break;
                    }
                    if(k==4) incrementChance();
                    if(playerDice.get(v).equals("Computer")){
                         computer(dice_output);
                        incrementChance();
                    }
                }




            }


            if(v == imgred) //april 5
            {
                if(playerDice.get(colorDice).equals("Computer"))
                    System.out.println("com  "+dice_output);
                else {
                    System.out.println("human  " + dice_output);
                    humanturn=false;
                }
                imgred.setImageResource(dice[diceNum]);
                boolean checkr=false;
                for(View vi:Key){
                    if(redToken.contains(vi)&&(safepoints.contains(tokenPos.get(vi))||iniSafePos.contains(tokenPos.get(vi)))){
                        ViewGroup vgrp=(ViewGroup) vi.getParent();
                        vgrp.removeView(vi);
                        vgrp.addView(vi);
                    }
                }
                int freq = Collections.frequency(isRedHome,true);
                if(freq+redWin==4&&redWin!=0) {
                    incrementChance();
                    checkr=true;
                }
                else  if(!isRedHome.contains(false)&& dice_output!=6){
                    incrementChance();
                    checkr=true;
                }
                else  if(!isRedHome.contains(false)&& dice_output==6) {
                    Random random2=new Random();
                    ImageView imgB=redToken.get(random2.nextInt(4));
                    FrameLayout resFrame=findViewById(R.id.ih14);
                    ViewGroup parent=(ViewGroup) imgB.getParent();
                    parent.removeView(imgB);
                    resFrame.addView(imgB);
                    tokenPos.put(imgB,resFrame);
                    // no token kill needed as starting point is safe point
                    isRedHome.set(redToken.indexOf(imgB),false);
                    checkr=true;
                    if(playerDice.get(imgred).equals("Computer"))
                        rolle(colorDice);
                }

                //if 3 at home 1 out and no six


                else if( dice_output!=6 && dice_output!=0)
                {

                    if (freq == 3) {
                        ImageView imb = redToken.get(isRedHome.indexOf(false));
                        autoChange(imb);
                        checkr=true;

                    }
                    //2 at home 1 out and no six
                    else if (freq == 2) {
                        ImageView imb1 = redToken.get(isRedHome.indexOf(false));
                        ImageView imb2 = redToken.get(isRedHome.lastIndexOf(false));

                        FrameLayout prevPos = tokenPos.get(imb1);
                        int count = redPath.indexOf(prevPos);
                        int index = count + dice_output;

                        FrameLayout prevPos1 = tokenPos.get(imb2);
                        int count1 = redPath.indexOf(prevPos1);
                        int index1 = count1 + dice_output;


                        if (index <= redPath.size() && index1 > redPath.size())
                        {
                            autoChange(imb1);
                            checkr=true;
                        }
                        else if (index1 <= redPath.size() && index > redPath.size())
                        {
                            autoChange(imb2);
                            checkr=true;
                        }

                    }
                    // 1 at home 3 out and no six
                    if (freq == 1) {
                        int count, count1, count2;
                        int index, index1, index2;
                        ImageView ntrue = redToken.get(isRedHome.indexOf(true));
                        for (int i = 0; i < redToken.size(); i++)
                        {
                            ImageView imb = redToken.get(i);
                            if (ntrue != imb)
                            {
                                redToken3.add(redToken.get(i));
                            }

                        }
                        ImageView imb1 = redToken3.get(0);
                        ImageView imb2 = redToken3.get(1);
                        ImageView imb3 = redToken3.get(2);

                        FrameLayout prevPos = tokenPos.get(imb1);
                        count = redPath.indexOf(prevPos);
                        index = count + dice_output;

                        FrameLayout prevPos1 = tokenPos.get(imb2);
                        count1 = redPath.indexOf(prevPos1);
                        index1 = count1 + dice_output;

                        FrameLayout prevPos2 = tokenPos.get(imb3);
                        count2 = redPath.indexOf(prevPos2);
                        index2 = count2 + dice_output;


                        if (index <= redPath.size() && index1 > redPath.size() && index2 > redPath.size()) {
                            autoChange(imb1);
                            checkr=true;
                        } else if (index1 <= redPath.size() && index > redPath.size() && index2 > redPath.size()) {
                            autoChange(imb2);
                            checkr=true;
                        } else if (index2 <= redPath.size() && index1 > redPath.size() && index > redPath.size()) {
                            autoChange(imb3);
                            checkr=true;
                        }

                    }
                }
                // all out means dice value can be anything
                else if(freq == 0 && dice_output!=0)
                {
                    int index,index1,index2,index3;
                    int count,count1,count2,count3;
                    ImageView imb1 = redToken3.get(0);
                    ImageView imb2 = redToken3.get(1);
                    ImageView imb3 = redToken3.get(2);
                    ImageView imb4 = redToken3.get(3);


                    FrameLayout prevPos = tokenPos.get(imb1);
                    count = redPath.indexOf(prevPos);
                    index = count+dice_output;

                    FrameLayout prevPos1 = tokenPos.get(imb2);
                    count1 = redPath.indexOf(prevPos1);
                    index1 = count1+dice_output;

                    FrameLayout prevPos2 = tokenPos.get(imb3);
                    count2 = redPath.indexOf(prevPos2);
                    index2 = count2+dice_output;

                    FrameLayout prevPos3 = tokenPos.get(imb4);
                    count3 = redPath.indexOf(prevPos3);
                    index3 = count3+dice_output;


                    if(index <= redPath.size() && index1 > redPath.size()&& index2 > redPath.size() && index3 > redPath.size())
                    {
                        autoChange(imb1);
                        checkr=true;
                    }
                    else if(index1 <=redPath.size() && index > redPath.size()&& index2 > redPath.size()&& index3 > redPath.size())
                    {
                        autoChange(imb2);
                        checkr=true;
                    }
                    else if(index2 <=redPath.size() && index1 > redPath.size() && index > redPath.size()&& index3 > redPath.size())
                    {
                        autoChange(imb3);
                        checkr=true;
                    }
                    else if(index3 <=redPath.size() && index1 > redPath.size() && index > redPath.size()&& index2 > redPath.size())
                    {
                        autoChange(imb4);
                        checkr=true;
                    }
                }
                if(!checkr){
                    int j=0;
                    FrameLayout redW=findViewById(R.id.ig9);
                  //  System.out.println("yesRed");
                    colorDice.setClickable(false);
                    for( j=0;j<4;j++){
                        if(redPath.indexOf(tokenPos.get(redToken.get(j)))+dice_output>56||isRedHome.get(j)==true||tokenPos.get(redToken.get(j))==redW)
                            continue;
                        else
                            break;
                    }
                    if(j==4) incrementChance();

                    if(playerDice.get(imgred).equals("Computer")){
                      //  System.out.println("if is working");
                        computer(dice_output);
                        incrementChance();

                    }
                }

            }
            if(v == imggreen) {  //april 5
                if(playerDice.get(colorDice).equals("Computer"))
                    System.out.println("com  "+dice_output);
                else {
                    System.out.println("human  " + dice_output);
                    humanturn=false;
                }
                imggreen.setImageResource(dice[diceNum]);
                boolean checkg=false;
                for(View vi:Key){
                    if(greenToken.contains(vi)&&(safepoints.contains(tokenPos.get(vi))||iniSafePos.contains(tokenPos.get(vi)))){
                        ViewGroup vgrp=(ViewGroup) vi.getParent();
                        vgrp.removeView(vi);
                        vgrp.addView(vi);
                    }
                }
                int freq = Collections.frequency(isGreenHome,true);
                if(freq+greenWin==4&&greenWin!=0){
                    incrementChance();
                    checkg=true;
                }
                else if(!isGreenHome.contains(false)&& dice_output!=6){
                    incrementChance();
                    checkg=true;
                }
                else if(!isGreenHome.contains(false)&& dice_output==6) {
                    Random random2=new Random();
                    ImageView imgB=greenToken.get(random2.nextInt(4));
                    FrameLayout resFrame=findViewById(R.id.ia9);
                    ViewGroup parent=(ViewGroup) imgB.getParent();
                    parent.removeView(imgB);
                    resFrame.addView(imgB);
                    tokenPos.put(imgB,resFrame);
                    // no token kill needed as starting point is safe point
                    isGreenHome.set(greenToken.indexOf(imgB),false);
                    checkg=true;
                    if(playerDice.get(imggreen).equals("Computer"))
                        rolle(colorDice);
                }

                //if 3 at home 1 out and no six


                else if( dice_output!=6 && dice_output!=0)
                {

                    if (freq == 3) {
                        ImageView imb = greenToken.get(isGreenHome.indexOf(false));
                        autoChange(imb);
                        checkg=true;
                    }
                    //2 at home 1 out and no six
                    else if (freq == 2) {
                        ImageView imb1 = greenToken.get(isGreenHome.indexOf(false));
                        ImageView imb2 = greenToken.get(isGreenHome.lastIndexOf(false));

                        FrameLayout prevPos = tokenPos.get(imb1);
                        int count = greenPath.indexOf(prevPos);
                        int index = count + dice_output;

                        FrameLayout prevPos1 = tokenPos.get(imb2);
                        int count1 = greenPath.indexOf(prevPos1);
                        int index1 = count1 + dice_output;


                        if (index <= greenPath.size() && index1 > greenPath.size())
                        {
                            autoChange(imb1);
                            checkg=true;
                        }
                        else if (index1 <= greenPath.size() && index > greenPath.size())
                        {
                            autoChange(imb2);
                            checkg=true;
                        }

                    }
                    // 1 at home 3 out and no six
                    else if (freq == 1) {
                        int count, count1, count2;
                        int index, index1, index2;
                        ImageView ntrue = greenToken.get(isGreenHome.indexOf(true));
                        for (int i = 0; i < greenToken.size(); i++)
                        {
                            ImageView imb = greenToken.get(i);
                            if (ntrue != imb)
                            {
                                greenToken3.add(greenToken.get(i));
                            }

                        }
                        ImageView imb1 = greenToken3.get(0);
                        ImageView imb2 = greenToken3.get(1);
                        ImageView imb3 = greenToken3.get(2);

                        FrameLayout prevPos = tokenPos.get(imb1);
                        count = greenPath.indexOf(prevPos);
                        index = count + dice_output;

                        FrameLayout prevPos1 = tokenPos.get(imb2);
                        count1 = greenPath.indexOf(prevPos1);
                        index1 = count1 + dice_output;

                        FrameLayout prevPos2 = tokenPos.get(imb3);
                        count2 = greenPath.indexOf(prevPos2);
                        index2 = count2 + dice_output;


                        if (index <= greenPath.size() && index1 > greenPath.size() && index2 > greenPath.size()) {
                            autoChange(imb1);
                            checkg=true;
                        } else if (index1 <= greenPath.size() && index > greenPath.size() && index2 > greenPath.size()) {
                            autoChange(imb2);
                            checkg=true;
                        } else if (index2 <= greenPath.size() && index1 > greenPath.size() && index > greenPath.size()) {
                            autoChange(imb3);
                            checkg=true;
                        }

                    }
                }
                // all out means dice value can be anything
                else if(freq == 0 && dice_output!=0)
                {
                    int index,index1,index2,index3;
                    int count,count1,count2,count3;
                    ImageView imb1 = greenToken3.get(0);
                    ImageView imb2 = greenToken3.get(1);
                    ImageView imb3 = greenToken3.get(2);
                    ImageView imb4 = greenToken3.get(3);


                    FrameLayout prevPos = tokenPos.get(imb1);
                    count = greenPath.indexOf(prevPos);
                    index = count+dice_output;

                    FrameLayout prevPos1 = tokenPos.get(imb2);
                    count1 = greenPath.indexOf(prevPos1);
                    index1 = count1+dice_output;

                    FrameLayout prevPos2 = tokenPos.get(imb3);
                    count2 = greenPath.indexOf(prevPos2);
                    index2 = count2+dice_output;

                    FrameLayout prevPos3 = tokenPos.get(imb4);
                    count3 = greenPath.indexOf(prevPos3);
                    index3 = count3+dice_output;


                    if(index <= greenPath.size() && index1 > greenPath.size()&& index2 > greenPath.size() && index3 > greenPath.size())
                    {
                        autoChange(imb1);
                        checkg=true;
                    }
                    else if(index1 <=greenPath.size() && index > greenPath.size()&& index2 > greenPath.size()&& index3 > greenPath.size())
                    {
                        autoChange(imb2);
                        checkg=true;
                    }
                    else if(index2 <=greenPath.size() && index1 > greenPath.size() && index > greenPath.size()&& index3 > greenPath.size())
                    {
                        autoChange(imb3);
                        checkg=true;
                    }
                    else if(index3 <=greenPath.size() && index1 > greenPath.size() && index > greenPath.size()&& index2 > greenPath.size())
                    {
                        autoChange(imb4);
                        checkg=true;
                    }
                }
                if(!checkg){
                    int p=0;
                    FrameLayout greenW=findViewById(R.id.if8);
                    System.out.println("yes");
                    colorDice.setClickable(false);
                    for( p=0;p<4;p++){
                        if(greenPath.indexOf(tokenPos.get(greenToken.get(p)))+dice_output>56||isGreenHome.get(p)==true||tokenPos.get(greenToken.get(p))==greenW)
                            continue;
                        else
                            break;
                    }
                    if(p==4) incrementChance();
                    if(playerDice.get(v).equals("Computer")){
                        computer(dice_output);
                        incrementChance();
                    }
                }


            }

            if(v == imgyel) {//4april
                if(playerDice.get(colorDice).equals("Computer"))
                    System.out.println("com  "+dice_output);
                else {
                    System.out.println("human  " + dice_output);
                    humanturn=false;
                }

                imgyel.setImageResource(dice[diceNum]);
                boolean checky=false;
                for(View vi:Key){
                    if(yellowToken.contains(vi)&&(safepoints.contains(tokenPos.get(vi))||iniSafePos.contains(tokenPos.get(vi)))){
                        ViewGroup vgrp=(ViewGroup) vi.getParent();
                        vgrp.removeView(vi);
                        vgrp.addView(vi);
                    }
                }
                int freq = Collections.frequency(isYellowHome,true);
                if(freq+yellowWin==4&&yellowWin!=0){
                    incrementChance();
                    checky=true;
                }
                else if(!isYellowHome.contains(false)&& dice_output!=6){
                    incrementChance();
                    checky=true;
                }

                else if(!isYellowHome.contains(false)&& dice_output==6) {
                    Random random2=new Random();
                    ImageView imgB=yellowToken.get(random2.nextInt(4));
                    FrameLayout resFrame=findViewById(R.id.in7);
                    ViewGroup parent=(ViewGroup) imgB.getParent();
                    parent.removeView(imgB);
                    resFrame.addView(imgB);
                    tokenPos.put(imgB,resFrame);
                    // no token kill needed as starting point is safe point
                    isYellowHome.set(yellowToken.indexOf(imgB),false);
                    checky=true;
                    if(playerDice.get(imgyel).equals("Computer"))
                        rolle(colorDice);
                }

                //if 3 at home 1 out and no six


                else  if( dice_output!=6 && dice_output!=0)
                {

                    if (freq == 3) {
                        ImageView imb = yellowToken.get(isYellowHome.indexOf(false));
                        autoChange(imb);
                        checky=true;
                    }
                    //2 at home 1 out and no six
                    else if (freq == 2) {
                        ImageView imb1 = yellowToken.get(isYellowHome.indexOf(false));
                        ImageView imb2 = yellowToken.get(isYellowHome.lastIndexOf(false));

                        FrameLayout prevPos = tokenPos.get(imb1);
                        int count = yellowPath.indexOf(prevPos);
                        int index = count + dice_output;

                        FrameLayout prevPos1 = tokenPos.get(imb2);
                        int count1 = yellowPath.indexOf(prevPos1);
                        int index1 = count1 + dice_output;


                        if (index <= yellowPath.size() && index1 > yellowPath.size())
                        {
                            autoChange(imb1);
                            checky=true;
                        }
                        else if (index1 <= yellowPath.size() && index > yellowPath.size())
                        {
                            autoChange(imb2);
                            checky=true;
                        }

                    }
                    // 1 at home 3 out and no six
                    else if (freq == 1) {
                        int count, count1, count2;
                        int index, index1, index2;
                        ImageView ntrue = yellowToken.get(isYellowHome.indexOf(true));
                        for (int i = 0; i < yellowToken.size(); i++)
                        {
                            ImageView imb = yellowToken.get(i);
                            if (ntrue != imb)
                            {
                                yellowToken3.add(yellowToken.get(i));
                            }

                        }
                        ImageView imb1 = yellowToken3.get(0);
                        ImageView imb2 = yellowToken3.get(1);
                        ImageView imb3 = yellowToken3.get(2);

                        FrameLayout prevPos = tokenPos.get(imb1);
                        count = yellowPath.indexOf(prevPos);
                        index = count + dice_output;

                        FrameLayout prevPos1 = tokenPos.get(imb2);
                        count1 = yellowPath.indexOf(prevPos1);
                        index1 = count1 + dice_output;

                        FrameLayout prevPos2 = tokenPos.get(imb3);
                        count2 = yellowPath.indexOf(prevPos2);
                        index2 = count2 + dice_output;


                        if (index <= yellowPath.size() && index1 > yellowPath.size() && index2 > yellowPath.size()) {
                            autoChange(imb1);
                            checky=true;
                        } else if (index1 <= yellowPath.size() && index > yellowPath.size() && index2 > yellowPath.size()) {
                            autoChange(imb2);
                            checky=true;
                        } else if (index2 <= yellowPath.size() && index1 > yellowPath.size() && index > yellowPath.size()) {
                            autoChange(imb3);
                            checky=true;
                        }

                    }
                }
                // all out means dice value can be anything
                else if(freq == 0 && dice_output!=0)
                {
                    int index,index1,index2,index3;
                    int count,count1,count2,count3;
                    ImageView imb1 = yellowToken3.get(0);
                    ImageView imb2 = yellowToken3.get(1);
                    ImageView imb3 = yellowToken3.get(2);
                    ImageView imb4 = yellowToken3.get(3);


                    FrameLayout prevPos = tokenPos.get(imb1);
                    count = yellowPath.indexOf(prevPos);
                    index = count+dice_output;

                    FrameLayout prevPos1 = tokenPos.get(imb2);
                    count1 = yellowPath.indexOf(prevPos1);
                    index1 = count1+dice_output;

                    FrameLayout prevPos2 = tokenPos.get(imb3);
                    count2 = yellowPath.indexOf(prevPos2);
                    index2 = count2+dice_output;

                    FrameLayout prevPos3 = tokenPos.get(imb4);
                    count3 = yellowPath.indexOf(prevPos3);
                    index3 = count3+dice_output;


                    if(index <= yellowPath.size() && index1 > yellowPath.size()&& index2 > yellowPath.size() && index3 > yellowPath.size())
                    {
                        autoChange(imb1);
                        checky=true;
                    }
                    else if(index1 <=yellowPath.size() && index > yellowPath.size()&& index2 > yellowPath.size()&& index3 > yellowPath.size())
                    {
                        autoChange(imb2);
                        checky=true;
                    }
                    else if(index2 <=yellowPath.size() && index1 > yellowPath.size() && index > yellowPath.size()&& index3 > yellowPath.size())
                    {
                        autoChange(imb3);
                        checky=true;
                    }
                    else if(index3 <=yellowPath.size() && index1 > yellowPath.size() && index > yellowPath.size()&& index2 > yellowPath.size())
                    {
                        autoChange(imb4);
                        checky=true;
                    }
                }

                if(!checky){
                    int q=0;
                    colorDice.setClickable(false);
                    System.out.println("yes");
                    FrameLayout yellowW=findViewById(R.id.ih8);
                    for( q=0;q<4;q++){
                        if(yellowPath.indexOf(tokenPos.get(yellowToken.get(q)))+dice_output>56||isYellowHome.get(q)==true||tokenPos.get(yellowToken.get(q))==yellowW)
                            continue;
                        else
                            break;
                    }
                    if(q==4) incrementChance();
                    if(playerDice.get(v).equals("Computer")){
                        computer(dice_output);
                        incrementChance();
                    }
                }


            }







        }


        public void autoChange(View v)//4april // 5 april updated
        {
            if (colorDice == imgblue && blueToken.contains((ImageView) v)) {
                FrameLayout prevPos = tokenPos.get(v);
                int count = bluePath.indexOf(prevPos);
                if(count+dice_output==56) {
                    blueWin++;
                    v.setClickable(false);
                }
                if (blueWin == 4) {
                    new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Blue")) + " Wins!!!")
                            .setIcon(R.drawable.winner_icon)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Database obj = new Database(vsComputer.this); //constructor is called of mydatabase class
                                    Integer tplay = 1, win =0;
                                    boolean update = false;

                                    if(Home.userName.equals(playerDice.get(colorDice)) ) {
                                        Integer winn = 1 ;
                                        System.out.println(playerDice.get(colorDice));
                                        update = obj.updateplay(winn, tplay, Home.userName);
                                    }
                                    else  if(!Home.userName.equals( playerDice.get(colorDice))) {
                                        System.out.println(playerDice.get(colorDice));
                                        update = obj.updateplay(win, tplay, Home.userName);
                                    }
                                    if (update) {
                                        Toast.makeText(vsComputer.this, "win updated", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(vsComputer.this, "not updated", Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(vsComputer.this,Home.class);
                                    intent.putExtra("username",pl1Name);
                                    startActivity(intent);


                                }
                            }).show();
                }
                if(count+dice_output>56)incrementChance();
                else {
                    FrameLayout newPos = bluePath.get(count + dice_output);
                    tokenPos.put(v, newPos);

                    //4april // 5 april
                    ImageView killpos = (ImageView) tokenkill.get(newPos);
                    if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)&& !iniSafePos.contains(newPos)&& !blueToken.contains(killpos)) {

                        prevPos.removeView(v);
                        tokenkill.remove(prevPos);
                        newPos.addView(v);
                        tokenkill.put(newPos, v);
                        BlueKilling(killpos);
                    } else {

                        tokenkill.put(newPos, v); //4april
                        tokenkill.remove(prevPos);
                        prevPos.removeView(v);

                        newPos.addView(v);
                        if(count+dice_output!=56)
                            incrementChance();
                    }
                }
dice_output=0;
            }
            //if token is yellow // 5april updated
            if (colorDice == imgyel && yellowToken.contains((ImageView) v)) {
                FrameLayout prevPos = tokenPos.get(v);
                int count = yellowPath.indexOf(prevPos);
                if(count+dice_output==56) {
                    yellowWin++;
                    v.setClickable(false);
                }
                if (yellowWin == 4) {
                    new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Yellow") )+ " Wins!!!")
                            .setIcon(R.drawable.winner_icon)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Database obj = new Database(vsComputer.this); //constructor is called of mydatabase class
                                    Integer tplay = 1, win =0;
                                    boolean update = false;

                                    if(Home.userName.equals(playerDice.get(colorDice)) ) {
                                        Integer winn = 1 ;
                                        System.out.println(playerDice.get(colorDice));
                                        update = obj.updateplay(winn, tplay, Home.userName);
                                    }
                                    else  if(!Home.userName.equals( playerDice.get(colorDice))) {
                                        System.out.println(playerDice.get(colorDice));
                                        update = obj.updateplay(win, tplay, Home.userName);
                                    }
                                    if (update) {
                                        Toast.makeText(vsComputer.this, "win updated", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(vsComputer.this, "not updated", Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(vsComputer.this,Home.class);
                                    intent.putExtra("username",pl1Name);
                                    startActivity(intent);
                                }
                            }).show();
                }
                if(count+dice_output>56)incrementChance();
                else {
                    FrameLayout newPos = yellowPath.get(count + dice_output);
                    tokenPos.put(v, newPos);
                    //4april
                    ImageView killpos = (ImageView) tokenkill.get(newPos);
                    if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)&& !iniSafePos.contains(newPos)&&!yellowToken.contains(killpos)) {


                        prevPos.removeView(v);
                        tokenkill.remove(prevPos);
                        newPos.addView(v);
                        tokenkill.put(newPos, v);
                        yellowKilling(killpos);
                    } else {
                        tokenkill.put(newPos, v);
                        tokenkill.remove(prevPos);//4april
                        prevPos.removeView(v);
                        newPos.addView(v);
                        if(count+dice_output!=56)
                            incrementChance();
                    }
                }
                dice_output=0;

            }
            //5april green token
            if (colorDice == imggreen && greenToken.contains((ImageView) v)) {
                FrameLayout prevPos = tokenPos.get(v);
                int count = greenPath.indexOf(prevPos);
                if(count+dice_output==56) {
                    greenWin++;
                    v.setClickable(false);
                }
                if (greenWin == 4) {
                    new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Green") )+ " Wins!!!")
                            .setIcon(R.drawable.winner_icon)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Database obj = new Database(vsComputer.this); //constructor is called of mydatabase class
                                    Integer tplay = 1, win =0;
                                    boolean update = false;

                                    if(Home.userName.equals(playerDice.get(colorDice)) ) {
                                        Integer winn = 1 ;
                                        System.out.println(playerDice.get(colorDice));
                                        update = obj.updateplay(winn, tplay, Home.userName);
                                    }
                                    else  if(!Home.userName.equals( playerDice.get(colorDice))) {
                                        System.out.println(playerDice.get(colorDice));
                                        update = obj.updateplay(win, tplay, Home.userName);
                                    }
                                    if (update) {
                                        Toast.makeText(vsComputer.this, "win updated", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(vsComputer.this, "not updated", Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(vsComputer.this,Home.class);
                                    intent.putExtra("username",pl1Name);
                                    startActivity(intent);

                                }
                            }).show();
                }
                if(count+dice_output>56)incrementChance();
                else {
                    FrameLayout newPos = greenPath.get(count + dice_output);
                    tokenPos.put(v, newPos);
                    ImageView killpos = (ImageView) tokenkill.get(newPos);
                    if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)&& !iniSafePos.contains(newPos)&&!greenToken.contains(killpos)) {

                        prevPos.removeView(v);
                        tokenkill.remove(prevPos);
                        newPos.addView(v);
                        tokenkill.put(newPos, v);
                        greenKilling(killpos);
                    } else {
                        tokenkill.put(newPos, v); //4april
                        tokenkill.remove(prevPos);
                        prevPos.removeView(v);
                        newPos.addView(v);
                        if(count+dice_output!=56)
                            incrementChance();
                    }
                }
                dice_output=0;
            }
            // 5 april redtoken
            if (colorDice == imgred && redToken.contains((ImageView) v)) {
                FrameLayout prevPos = tokenPos.get(v);
                int count = redPath.indexOf(prevPos);
                if(count+dice_output==56) {
                    redWin++;
                    v.setClickable(false);
                }
                if (redWin == 4) {
                    new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Red") )+ " Wins!!!")
                            .setIcon(R.drawable.winner_icon)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Database obj = new Database(vsComputer.this); //constructor is called of mydatabase class
                                    Integer tplay = 1, win =0;
                                    boolean update = false;

                                    if(Home.userName.equals(playerDice.get(colorDice)) ) {
                                        Integer winn = 1 ;
                                        System.out.println(playerDice.get(colorDice));
                                        update = obj.updateplay(winn, tplay, Home.userName);
                                    }
                                    else  if(!Home.userName.equals( playerDice.get(colorDice))) {
                                        System.out.println(playerDice.get(colorDice));
                                        update = obj.updateplay(win, tplay, Home.userName);
                                    }
                                    if (update) {
                                        Toast.makeText(vsComputer.this, "win updated", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(vsComputer.this, "not updated", Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(vsComputer.this,Home.class);
                                    intent.putExtra("username",pl1Name);
                                    startActivity(intent);

                                }
                            }).show();
                }
                if(count+dice_output>56)incrementChance();
                else {
                    FrameLayout newPos = redPath.get(count + dice_output);
                    tokenPos.put(v, newPos);
                    ImageView killpos = (ImageView) tokenkill.get(newPos);
                    if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)&& !iniSafePos.contains(newPos)&&!redToken.contains(killpos)) {

                        prevPos.removeView(v);
                        newPos.addView(v);
                        tokenkill.put(newPos, v);
                        redKilling(killpos);
                    } else {
                        tokenkill.put(newPos, v); //4april
                        tokenkill.remove(prevPos);
                        prevPos.removeView(v);
                        newPos.addView(v);
                        if(count+dice_output!=56)
                            incrementChance();
                    }
                }
              dice_output=0;
            }
        }
        //BlueKilling Tokens Function  5april
        public void BlueKilling(View v)
        {
            if(yellowToken.contains(v))
            {



                int ret = yellowToken.indexOf(v);
                FrameLayout retpos =  yellowHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);

                System.out.println("y k by b"+" "+ dice_output);
                isYellowHome.set(yellowToken.indexOf(v),true);
            }
            else if(greenToken.contains(v))
            {

                int ret = greenToken.indexOf(v);
                FrameLayout retpos =  greenHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);
                //tokenPos.put(v,retpos);
                System.out.println("g k by b"+" "+ dice_output);
                isGreenHome.set(greenToken.indexOf(v),true);
            }
            else if(redToken.contains(v))
            {


                int ret = redToken.indexOf(v);
                FrameLayout retpos =  redHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);
                //  tokenPos.put(v,retpos);
                System.out.println("r k by b"+" "+ dice_output);
                isRedHome.set(redToken.indexOf(v),true);
            }

        }
        public void yellowKilling(View v) //5april
        {

            if(blueToken.contains(v))
            {

                int ret = blueToken.indexOf(v);
                FrameLayout retpos =  blueHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);
                System.out.println("b k by y"+" "+ dice_output);
                // tokenPos.put(v,retpos);
                isBlueHome.set(blueToken.indexOf(v),true);
            }
            else if(greenToken.contains(v))
            {
                int ret = greenToken.indexOf(v);
                FrameLayout retpos =  greenHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);
                System.out.println("g k by y"+" "+ dice_output);
                // tokenPos.put(v,retpos);
                isGreenHome.set(greenToken.indexOf(v),true);
            }
            else if(redToken.contains(v))
            {
                int ret = redToken.indexOf(v);
                FrameLayout retpos =  redHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);
                //tokenPos.put(v,retpos);
                System.out.println("r k by y"+" "+ dice_output);
                isRedHome.set(redToken.indexOf(v),true);
            }

        }
        //redKilling 5 april
        public void redKilling(View v)
        {
            if(blueToken.contains(v))
            {
                int ret = blueToken.indexOf(v);
                FrameLayout retpos =  blueHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);
                //tokenPos.put(v,retpos);
                System.out.println("b k by r"+" "+ dice_output);
                isBlueHome.set(blueToken.indexOf(v),true);

            }
            else if(greenToken.contains(v))
            {
                int ret = greenToken.indexOf(v);
                FrameLayout retpos =  greenHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);
                System.out.println("g k by r"+" "+ dice_output);
                //  tokenPos.put(v,retpos);
                isGreenHome.set(greenToken.indexOf(v),true);
            }
            else if(yellowToken.contains(v))
            {
                int ret = yellowToken.indexOf(v);
                FrameLayout retpos =  yellowHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);
                System.out.println("y k by r"+" "+ dice_output);
                //  tokenPos.put(v,retpos);
                isYellowHome.set(yellowToken.indexOf(v),true);
            }

        }
        public void greenKilling(View v)
        {
            if(blueToken.contains(v))
            {
                int ret = blueToken.indexOf(v);
                FrameLayout retpos =  blueHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);
                //  tokenPos.put(v,retpos);
                System.out.println("blue k by g"+" "+ dice_output);
                isBlueHome.set(blueToken.indexOf(v),true);
            }
            else if(redToken.contains(v))
            {
                int ret = redToken.indexOf(v);
                FrameLayout retpos =  redHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);
                // tokenPos.put(v,retpos);
                System.out.println("r k by g"+" "+ dice_output);
                isRedHome.set(redToken.indexOf(v),true);
            }
            else if(yellowToken.contains(v))
            {
                int ret = yellowToken.indexOf(v);
                FrameLayout retpos =  yellowHome.get(ret);

                ViewGroup parent =(ViewGroup) v.getParent();
                parent.removeView(v);
                retpos.addView(v);
                // tokenPos.put(v,retpos);
                System.out.println("y k by g"+" "+ dice_output);
                isYellowHome.set(yellowToken.indexOf(v),true);
            }
            if(playerDice.get(imgred)=="Computer"){
                rolle(imgred);
                while(dice_output==6){
                    rolle(imgred);
                }
            }
        }
        // Changing the position of view according to the outcome of dice
        public void posChange(View v) {//4 april

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FrameLayout prevPos = tokenPos.get(v);

                    System.out.println(dice_output);
                    if (dice_output != 0) {

                        if (colorDice == imgblue && blueToken.contains((ImageView) v)) {
                            colorDice.setClickable(true);

                            if (dice_output == 6 && isBlueHome.get(blueToken.indexOf(v)) == true) {
                                FrameLayout resFrame = findViewById(R.id.if2);
                                ViewGroup parent = (ViewGroup) v.getParent();
                                parent.removeView(v);
                                tokenkill.remove(prevPos);
                                resFrame.addView(v);
                                tokenPos.put(v, resFrame);

                                isBlueHome.set(blueToken.indexOf(v), false);


                            } else if (isBlueHome.get(blueToken.indexOf(v)) == false) {
                                int count = bluePath.indexOf(prevPos);
                                if (count + dice_output == 56) blueWin++;
                                if (blueWin == 4) {
                                    new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Blue") )+ " Wins!!!")
                                            .setIcon(R.drawable.winner_icon)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Database obj = new Database(vsComputer.this); //constructor is called of mydatabase class
                                                    Integer tplay = 1, win =0;
                                                    boolean update = false;

                                                    if(Home.userName.equals(playerDice.get(colorDice)) ) {
                                                        Integer winn = 1 ;
                                                        System.out.println(playerDice.get(colorDice));
                                                        update = obj.updateplay(winn, tplay, Home.userName);
                                                    }
                                                    else  if(!Home.userName.equals( playerDice.get(colorDice))) {
                                                        System.out.println(playerDice.get(colorDice));
                                                        update = obj.updateplay(win, tplay, Home.userName);
                                                    }
                                                    if (update) {
                                                        Toast.makeText(vsComputer.this, "win updated", Toast.LENGTH_SHORT).show();
                                                    } else
                                                        Toast.makeText(vsComputer.this, "not updated", Toast.LENGTH_SHORT).show();

                                                    Intent intent=new Intent(vsComputer.this,Home.class);
                                                    intent.putExtra("username",pl1Name);
                                                    startActivity(intent);
                                                }
                                            }).show();
                                }
                                if (count + dice_output <= 56) {
                                    FrameLayout newPos = bluePath.get(count + dice_output);
                                    tokenPos.put(v, newPos);
                                    ImageView killpos = (ImageView) tokenkill.get(newPos);
                                    if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)&& !iniSafePos.contains(newPos)&&!blueToken.contains(killpos)) {

                                        prevPos.removeView(v);
                                        tokenkill.remove(prevPos);
                                        newPos.addView(v);
                                        tokenkill.put(newPos, v);//8-04
                                        BlueKilling(killpos);
                                        Dice.setClickable(true);//9-04

                                    } else {
                                        tokenkill.put(newPos, v);
                                        tokenkill.remove(prevPos);
                                        prevPos.removeView(v);
                                        newPos.addView(v);
                                        if(count+dice_output!=56)
                                            incrementChance();
                                    }

                                }
                            }

                            if((dice_output!=6&&isBlueHome.get(blueToken.indexOf(v))==true)||bluePath.indexOf(tokenPos.get(v))==56)
                                dice_output=dice_output;
                            else
                                dice_output = 0;
                        }

                        //5 april updated
                        else if (colorDice == imgyel && yellowToken.contains((ImageView) v)) {
                            colorDice.setClickable(true);

                            if (dice_output == 6 && isYellowHome.get(yellowToken.indexOf(v)) == true) {

                                FrameLayout resFrame = findViewById(R.id.in7);
                                ViewGroup parent = (ViewGroup) v.getParent();
                                parent.removeView(v);
                                tokenkill.remove(prevPos);
                                resFrame.addView(v);
                                tokenPos.put(v, resFrame);

                                isYellowHome.set(yellowToken.indexOf(v), false);
                                Dice.setClickable(true);//9-04


                            } else if (isYellowHome.get(yellowToken.indexOf(v)) == false) {

                                int count = yellowPath.indexOf(prevPos);
                                if (count + dice_output == 56) yellowWin++;
                                if (yellowWin == 4) {
                                    new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Yellow")) + " Wins!!!")
                                            .setIcon(R.drawable.winner_icon)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            }).show();
                                }
                                if (count + dice_output <= 56) {
                                    FrameLayout newPos = yellowPath.get(count + dice_output);
                                    tokenPos.put(v, newPos);
                                    ImageView killpos = (ImageView) tokenkill.get(newPos);
                                    if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)&& !iniSafePos.contains(newPos)&&!yellowToken.contains(killpos)) {

                                        prevPos.removeView(v);
                                        tokenkill.remove(prevPos);
                                        newPos.addView(v);
                                        tokenkill.put(newPos, v);//8-04
                                        yellowKilling(killpos);
                                        Dice.setClickable(true);//9-04
                                    } else {
                                        tokenkill.put(newPos, v);
                                        tokenkill.remove(prevPos);
                                        prevPos.removeView(v);
                                        newPos.addView(v);
                                        if(count+dice_output!=56)
                                            incrementChance();
                                    }
                                }
                            }
                            if((dice_output!=6&&isYellowHome.get(yellowToken.indexOf(v))==true)||yellowPath.indexOf(tokenPos.get(v))==56)
                                dice_output=dice_output;
                            else
                                dice_output = 0;
                        }


                        //5 april updated
                        else if (colorDice == imgred && redToken.contains((ImageView) v)) {
                            colorDice.setClickable(true);
                            if (dice_output == 6 && isRedHome.get(redToken.indexOf(v)) == true) {

                                FrameLayout resFrame = findViewById(R.id.ih14);
                                ViewGroup parent = (ViewGroup) v.getParent();
                                parent.removeView(v);
                                resFrame.addView(v);
                                tokenPos.put(v, resFrame);

                                isRedHome.set(redToken.indexOf(v), false);


                            } else if (isRedHome.get(redToken.indexOf(v)) == false) {

                                int count = redPath.indexOf(prevPos);
                                if (count + dice_output == 56) redWin++;
                                if (redWin == 4) {
                                    new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Red")) + " Wins!!!")
                                            .setIcon(R.drawable.winner_icon)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            }).show();
                                }
                                if (count + dice_output <= 56) {
                                    FrameLayout newPos = redPath.get(count + dice_output);
                                    tokenPos.put(v, newPos);

                                    ImageView killpos = (ImageView) tokenkill.get(newPos);
                                    if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)&& !iniSafePos.contains(newPos)&&!redToken.contains(killpos)) {

                                        prevPos.removeView(v);
                                        tokenkill.remove(prevPos);
                                        newPos.addView(v);
                                        tokenkill.put(newPos, v);//8-04
                                        redKilling(killpos);


                                    } else {
                                        tokenkill.put(newPos, v);
                                        tokenkill.remove(prevPos);
                                        prevPos.removeView(v);
                                        newPos.addView(v);
                                        if(count+dice_output!=56)
                                            incrementChance();
                                    }

                                }
                            }
                            if ((dice_output != 6 && isRedHome.get(redToken.indexOf(v))==true)|| redPath.indexOf(tokenPos.get(v)) == 56)
                                dice_output = dice_output;
                            else {

                                dice_output = 0;
                            }
                        }

                        //5 april updated
                        else if (colorDice == imggreen && greenToken.contains((ImageView) v)) {
                            colorDice.setClickable(true);
                            System.out.println(dice_output + " Green");
                            if (dice_output == 6 && isGreenHome.get(greenToken.indexOf(v)) == true) {
                                System.out.println(dice_output + " Green inside if");
                                FrameLayout resFrame = findViewById(R.id.ia9);
                                ViewGroup parent = (ViewGroup) v.getParent();
                                parent.removeView(v);
                                resFrame.addView(v);
                                tokenPos.put(v, resFrame);

                                isGreenHome.set(greenToken.indexOf(v), false);



                            } else if (isGreenHome.get(greenToken.indexOf(v)) == false) {

                                int count = greenPath.indexOf(prevPos);
                                if (count + dice_output == 56) greenWin++;
                                if (greenWin == 4) {
                                    new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Green") )+ " Wins!!!")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            }).show();
                                }
                                if (count + dice_output <= 56) {
                                    FrameLayout newPos = greenPath.get(count + dice_output);
                                    tokenPos.put(v, newPos);
                                    ImageView killpos = (ImageView) tokenkill.get(newPos);
                                    if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)&& !iniSafePos.contains(newPos)&&!greenToken.contains(killpos)) {

                                        prevPos.removeView(v);
                                        tokenkill.remove(prevPos);
                                        newPos.addView(v);
                                        tokenkill.put(newPos, v);//8-04
                                        greenKilling(killpos);


                                    } else {
                                        tokenkill.put(newPos, v);
                                        tokenkill.remove(prevPos);
                                        prevPos.removeView(v);
                                        newPos.addView(v);
                                        if(count+dice_output!=56)
                                            incrementChance();
                                    }

                                }
                            }
                            if((dice_output!=6&&isGreenHome.get(greenToken.indexOf(v))==true)||greenPath.indexOf(tokenPos.get(v))==56)
                                dice_output=dice_output;
                            else
                                dice_output = 0;
                        }



                    }



                }

            });
        }

          public void computer(int dice_output){

              FrameLayout prevPos = null;
              boolean isCom=false;
              int iswin=0;
            HashMap<View,FrameLayout> tempPos=new HashMap<>();
            Set<View>keyImg=tokenPos.keySet();
            // Calculating f(n)
            if(colorDice==imgred){
                for(View vw:keyImg){
                    if(redToken.contains(vw)){
                        FrameLayout pPos=tokenPos.get(vw);
                        if(redPath.indexOf(pPos)+dice_output<=56)
                        tempPos.put(vw,redPath.get(redPath.indexOf(pPos)+dice_output)); } }
                for(int i = 0;i<4;i++) {
                    if (tempPos.containsKey(redToken.get(i))) {
                        iswin = redPath.indexOf(tempPos.get(redToken.get(i)));
                        FrameLayout newPos = redPath.get(iswin);
                        // Goal state 1 ......Win.......
                        if (iswin == 56 ) {
                            isCom = true;

                            ImageView img = redToken.get(i);
                            prevPos = tokenPos.get(img);

                            tokenPos.put(img, newPos);
                            prevPos.removeView(img);
                            newPos.addView(img);
                            redWin++;
                            if (redWin == 4) {
                                new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Red") + " Wins!!!"))
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Database obj = new Database(vsComputer.this); //constructor is called of mydatabase class
                                                Integer tplay = 1, win =0;
                                                boolean update = false;

                                                if(Home.userName.equals(playerDice.get(colorDice)) ) {
                                                    Integer winn = 1 ;
                                                    System.out.println(playerDice.get(colorDice));
                                                    update = obj.updateplay(winn, tplay, Home.userName);
                                                }
                                                else  if(!Home.userName.equals( playerDice.get(colorDice))) {
                                                    System.out.println(playerDice.get(colorDice));
                                                    update = obj.updateplay(win, tplay, Home.userName);
                                                }
                                                if (update) {
                                                    Toast.makeText(vsComputer.this, "win updated", Toast.LENGTH_SHORT).show();
                                                } else
                                                    Toast.makeText(vsComputer.this, "not updated", Toast.LENGTH_SHORT).show();

                                                Intent intent=new Intent(vsComputer.this,Home.class);
                                                intent.putExtra("username",pl1Name);
                                                startActivity(intent);
                                            }
                                        }).show();
                            }
                            rolle(colorDice);
                            break;

                        }
                        else if (iswin >= 51 && iswin <= 56) {
                            isCom = true;
                            ImageView img = redToken.get(i);
                            prevPos = tokenPos.get(img);

                            tokenPos.put(img, newPos);
                            prevPos.removeView(img);
                            newPos.addView(img);
                            break;
                        }
                        // Goal state 2 ......Kill.......
                        else if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)) {

                            ImageView img = redToken.get(i);
                            prevPos = tokenPos.get(img);
                            tokenPos.put(img, newPos);

                            ImageView killpos = (ImageView) tokenkill.get(newPos);
                            if (!redToken.contains(killpos)) {
                                isCom = true;
                                prevPos.removeView(img);
                                tokenkill.remove(prevPos);
                                newPos.addView(img);
                                tokenkill.put(newPos, img);
                                redKilling(killpos);

                                rolle(colorDice);
                            }
                        } } }
                // Goal state 3 ......Move Out Of Home.......
                if (!isCom && dice_output == 6 && isRedHome.contains(true)) {
        isCom=true;
        View move = redToken.get(isRedHome.indexOf(true));
        FrameLayout resPos = findViewById(R.id.ih14);
        System.out.println("yes in 3");
        ViewGroup oldPos = (ViewGroup) move.getParent();
        oldPos.removeView(move);
        resPos.addView(move);
        isRedHome.set(redToken.indexOf(move), false);
        tokenPos.put(move, resPos);
        rolle(colorDice);

    }
                // Goal state 4......Move any dice at random with possible movement.......
                if(!isCom){

                for(int i=0;i<4;i++){

                    View rdToken=redToken.get(i);
                    if(isRedHome.get(i)==false&&redPath.indexOf(tokenPos.get(rdToken))!=56){
                        System.out.println("yes in 4 ");
                        ViewGroup oldParent=(ViewGroup) rdToken.getParent();
                        oldParent.removeView(rdToken);
                        tempPos.get(rdToken).addView(rdToken);
                        tokenPos.put(rdToken,tempPos.get(rdToken));
                        break;
                    }
                }
                if (dice_output==6) {
                    System.out.println(" 4 "+ dice_output);
                    rolle(colorDice);
                }
                else
                    return;
            }

            }
              if(colorDice==imgyel){
                  for(View vw:keyImg){
                      if(yellowToken.contains(vw)){
                          FrameLayout pPos=tokenPos.get(vw);
                          if(yellowPath.indexOf(pPos)+dice_output<=56)
                              tempPos.put(vw,yellowPath.get(yellowPath.indexOf(pPos)+dice_output));
                      }
                  }
                  //......Condition 1.........Priority 1......If any win possible.......
                  for(int i = 0;i<4;i++) {
                      if (tempPos.containsKey(yellowToken.get(i))) {
                          iswin = yellowPath.indexOf(tempPos.get(yellowToken.get(i)));
                          FrameLayout newPos = yellowPath.get(iswin);

                          if (iswin == 56 ) {
                              isCom = true;

                              ImageView img = yellowToken.get(i);
                              prevPos = tokenPos.get(img);

                              tokenPos.put(img, newPos);
                              prevPos.removeView(img);
                              newPos.addView(img);
                              yellowWin++;
                              if (yellowWin == 4) {
                                  new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Yellow")) + " Wins!!!")
                                          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialogInterface, int i) {
                                                  Database obj = new Database(vsComputer.this); //constructor is called of mydatabase class
                                                  Integer tplay = 1, win =0;
                                                  boolean update = false;

                                                  if(Home.userName.equals(playerDice.get(colorDice)) ) {
                                                      Integer winn = 1 ;
                                                      System.out.println(playerDice.get(colorDice));
                                                      update = obj.updateplay(winn, tplay, Home.userName);
                                                  }
                                                  else  if(!Home.userName.equals( playerDice.get(colorDice))) {
                                                      System.out.println(playerDice.get(colorDice));
                                                      update = obj.updateplay(win, tplay, Home.userName);
                                                  }
                                                  if (update) {
                                                      Toast.makeText(vsComputer.this, "win updated", Toast.LENGTH_SHORT).show();
                                                  } else
                                                      Toast.makeText(vsComputer.this, "not updated", Toast.LENGTH_SHORT).show();

                                                  Intent intent=new Intent(vsComputer.this,Home.class);
                                                  intent.putExtra("username",pl1Name);
                                                  startActivity(intent);
                                                  //dialogInterface.dismiss();
                                              }
                                          }).show();
                              }
                              rolle(colorDice);
                              break;

                          } else if (iswin >= 51 && iswin <= 56) {
                              isCom = true;
                              ImageView img = yellowToken.get(i);
                              prevPos = tokenPos.get(img);

                              tokenPos.put(img, newPos);
                              prevPos.removeView(img);
                              newPos.addView(img);
                              break;
                          } else if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)) {

                              ImageView img = yellowToken.get(i);
                              prevPos = tokenPos.get(img);
                              tokenPos.put(img, newPos);

                              ImageView killpos = (ImageView) tokenkill.get(newPos);
                              if (!yellowToken.contains(killpos)) {
                                  isCom = true;
                                  prevPos.removeView(img);
                                  tokenkill.remove(prevPos);
                                  newPos.addView(img);
                                  tokenkill.put(newPos, img);
                                  yellowKilling(killpos);
                                  rolle(colorDice);
                              }
                          }


                      }

                  }


                  if (!isCom && dice_output == 6 && isYellowHome.contains(true)) {
                      isCom=true;
                      View move = yellowToken.get(isYellowHome.indexOf(true));
                      FrameLayout resPos = findViewById(R.id.in7);
                      System.out.println("yes in 3");
                      ViewGroup oldPos = (ViewGroup) move.getParent();
                      oldPos.removeView(move);
                      resPos.addView(move);
                      isYellowHome.set(yellowToken.indexOf(move), false);
                      tokenPos.put(move, resPos);
                      rolle(colorDice);

                  }

                  if(!isCom){

                      for(int i=0;i<4;i++){

                          View ylToken=yellowToken.get(i);
                          if(isYellowHome.get(i)==false&&yellowPath.indexOf(tokenPos.get(ylToken))!=56){
                              System.out.println("yes in 4 ");
                              ViewGroup oldParent=(ViewGroup) ylToken.getParent();
                              oldParent.removeView(ylToken);
                              tempPos.get(ylToken).addView(ylToken);
                              tokenPos.put(ylToken,tempPos.get(ylToken));
                              break;
                          }
                      }
                      if (dice_output==6) {
                          System.out.println(" 4 "+ dice_output);
                          rolle(colorDice);
                      }
                      else
                          return;
                  }

              }
              if(colorDice==imgblue){
                  for(View vw:keyImg){
                      if(blueToken.contains(vw)){
                          FrameLayout pPos=tokenPos.get(vw);
                          if(bluePath.indexOf(pPos)+dice_output<=56)
                              tempPos.put(vw,bluePath.get(bluePath.indexOf(pPos)+dice_output));
                      }
                  }
                  //......Condition 1.........Priority 1......If any win possible.......
                  for(int i = 0;i<4;i++) {
                      if (tempPos.containsKey(blueToken.get(i))) {
                          iswin = bluePath.indexOf(tempPos.get(blueToken.get(i)));
                          FrameLayout newPos = bluePath.get(iswin);

                          if (iswin == 56 ) {
                              isCom = true;

                              ImageView img = blueToken.get(i);
                              prevPos = tokenPos.get(img);

                              tokenPos.put(img, newPos);
                              prevPos.removeView(img);
                              newPos.addView(img);
                              blueWin++;
                              if (blueWin == 4) {
                                  new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Blue") )+ " Wins!!!")
                                          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialogInterface, int i) {
                                                  Database obj = new Database(vsComputer.this); //constructor is called of mydatabase class
                                                  Integer tplay = 1, win =0;
                                                  boolean update = false;

                                                  if(Home.userName.equals(playerDice.get(colorDice)) ) {
                                                      Integer winn = 1 ;
                                                      System.out.println(playerDice.get(colorDice));
                                                      update = obj.updateplay(winn, tplay, Home.userName);
                                                  }
                                                  else  if(!Home.userName.equals( playerDice.get(colorDice))) {
                                                      System.out.println(playerDice.get(colorDice));
                                                      update = obj.updateplay(win, tplay, Home.userName);
                                                  }
                                                  if (update) {
                                                      Toast.makeText(vsComputer.this, "win updated", Toast.LENGTH_SHORT).show();
                                                  } else
                                                      Toast.makeText(vsComputer.this, "not updated", Toast.LENGTH_SHORT).show();

                                                  Intent intent=new Intent(vsComputer.this,Home.class);
                                                  intent.putExtra("username",pl1Name);
                                                  startActivity(intent);

                                                  //dialogInterface.dismiss();
                                              }
                                          }).show();
                              }
                              rolle(colorDice);
                              break;

                          } else if (iswin >= 51 && iswin <= 56) {
                              isCom = true;
                              ImageView img = blueToken.get(i);
                              prevPos = tokenPos.get(img);

                              tokenPos.put(img, newPos);
                              prevPos.removeView(img);
                              newPos.addView(img);
                              break;
                          } else if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)) {

                              ImageView img = blueToken.get(i);
                              prevPos = tokenPos.get(img);
                              tokenPos.put(img, newPos);

                              ImageView killpos = (ImageView) tokenkill.get(newPos);
                              if (!blueToken.contains(killpos)) {
                                  isCom = true;
                                  prevPos.removeView(img);
                                  tokenkill.remove(prevPos);
                                  newPos.addView(img);
                                  tokenkill.put(newPos, img);
                                  BlueKilling(killpos);
                                  rolle(colorDice);
                              }
                          }


                      }

                  }


                  if (!isCom && dice_output == 6 && isBlueHome.contains(true)) {
                      isCom=true;
                      View move = blueToken.get(isBlueHome.indexOf(true));
                      FrameLayout resPos = findViewById(R.id.if2);
                      System.out.println("yes in 3");
                      ViewGroup oldPos = (ViewGroup) move.getParent();
                      oldPos.removeView(move);
                      resPos.addView(move);
                      isBlueHome.set(blueToken.indexOf(move), false);
                      tokenPos.put(move, resPos);
                      rolle(colorDice);

                  }

                  if(!isCom){

                      for(int i=0;i<4;i++){

                          View blToken=blueToken.get(i);
                          if(isBlueHome.get(i)==false&&bluePath.indexOf(tokenPos.get(blToken))!=56){
                              System.out.println("yes in 4 ");
                              ViewGroup oldParent=(ViewGroup) blToken.getParent();
                              oldParent.removeView(blToken);
                              tempPos.get(blToken).addView(blToken);
                              tokenPos.put(blToken,tempPos.get(blToken));
                              break;
                          }
                      }
                      if (dice_output==6) {
                          System.out.println(" 4 "+ dice_output);
                          rolle(colorDice);
                      }
                      else
                          return;
                  }

              }
              if(colorDice==imggreen){
                  for(View vw:keyImg){
                      if(greenToken.contains(vw)){
                          FrameLayout pPos=tokenPos.get(vw);
                          if(greenPath.indexOf(pPos)+dice_output<=56)
                              tempPos.put(vw,greenPath.get(greenPath.indexOf(pPos)+dice_output));
                      }
                  }
                  //......Condition 1.........Priority 1......If any win possible.......
                  for(int i = 0;i<4;i++) {
                      if (tempPos.containsKey(greenToken.get(i))) {
                          iswin = greenPath.indexOf(tempPos.get(greenToken.get(i)));
                          FrameLayout newPos = greenPath.get(iswin);

                          if (iswin == 56 ) {
                              isCom = true;

                              ImageView img = greenToken.get(i);
                              prevPos = tokenPos.get(img);

                              tokenPos.put(img, newPos);
                              prevPos.removeView(img);
                              newPos.addView(img);
                              greenWin++;
                              if (greenWin == 4) {
                                  new AlertDialog.Builder(vsComputer.this).setTitle(playerDice.get(diceColour.get("Green")) + " Wins!!!")
                                          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialogInterface, int i) {
                                                  Database obj = new Database(vsComputer.this); //constructor is called of mydatabase class
                                                  Integer tplay = 1, win =0;
                                                  boolean update = false;

                                                  if(Home.userName.equals(playerDice.get(colorDice)) ) {
                                                      Integer winn = 1 ;
                                                      System.out.println(playerDice.get(colorDice));
                                                      update = obj.updateplay(winn, tplay, Home.userName);
                                                  }
                                                  else  if(!Home.userName.equals( playerDice.get(colorDice))) {
                                                      System.out.println(playerDice.get(colorDice));
                                                      update = obj.updateplay(win, tplay, Home.userName);
                                                  }
                                                  if (update) {
                                                      Toast.makeText(vsComputer.this, "win updated", Toast.LENGTH_SHORT).show();
                                                  } else
                                                      Toast.makeText(vsComputer.this, "not updated", Toast.LENGTH_SHORT).show();

                                                  Intent intent=new Intent(vsComputer.this,Home.class);
                                                  intent.putExtra("username",pl1Name);
                                                  startActivity(intent);
                                                 // dialogInterface.dismiss();
                                              }
                                          }).show();
                              }
                              rolle(colorDice);
                              break;

                          } else if (iswin >= 51 && iswin <= 56) {
                              isCom = true;
                              ImageView img = greenToken.get(i);
                              prevPos = tokenPos.get(img);

                              tokenPos.put(img, newPos);
                              prevPos.removeView(img);
                              newPos.addView(img);
                              break;
                          } else if (tokenkill.containsKey(newPos) && !safepoints.contains(newPos)) {

                              ImageView img = greenToken.get(i);
                              prevPos = tokenPos.get(img);
                              tokenPos.put(img, newPos);

                              ImageView killpos = (ImageView) tokenkill.get(newPos);
                              if (!greenToken.contains(killpos)) {
                                  isCom = true;
                                  prevPos.removeView(img);
                                  tokenkill.remove(prevPos);
                                  newPos.addView(img);
                                  tokenkill.put(newPos, img);
                                  greenKilling(killpos);
                                  rolle(colorDice);
                              }
                          }


                      }

                  }


                  if (!isCom && dice_output == 6 && isGreenHome.contains(true)) {
                      isCom=true;
                      View move = greenToken.get(isGreenHome.indexOf(true));
                      FrameLayout resPos = findViewById(R.id.ia9);
                      System.out.println("yes in 3");
                      ViewGroup oldPos = (ViewGroup) move.getParent();
                      oldPos.removeView(move);
                      resPos.addView(move);
                      isGreenHome.set(greenToken.indexOf(move), false);
                      tokenPos.put(move, resPos);
                      rolle(colorDice);

                  }

                  if(!isCom){

                      for(int i=0;i<4;i++){

                          View grToken=greenToken.get(i);
                          if(isGreenHome.get(i)==false&&greenPath.indexOf(tokenPos.get(grToken))!=56){
                              System.out.println("yes in 4 ");
                              ViewGroup oldParent=(ViewGroup) grToken.getParent();
                              oldParent.removeView(grToken);
                              tempPos.get(grToken).addView(grToken);
                              tokenPos.put(grToken,tempPos.get(grToken));
                              break;
                          }
                      }
                      if (dice_output==6) {
                          System.out.println(" 4 "+ dice_output);
                          rolle(colorDice);
                      }
                      else
                          return;
                  }

              }


}
        public void mapPlayerToDice(){
            Intent intent=getIntent();
            pl1Name=intent.getStringExtra("Pl1Name");
            System.out.println(pl1Name);
            pl2Name=intent.getStringExtra("Pl2Name");
            pl3Name=intent.getStringExtra("Pl3Name");
            pl4Name=intent.getStringExtra("Pl4Name");
            pl1Color=intent.getStringExtra("Pl1Choice");
            pl2Color=intent.getStringExtra("Pl2Choice");
            pl3Color=intent.getStringExtra("Pl3Choice");
            pl4Color=intent.getStringExtra("Pl4Choice");
            //......setting sequence of dice to move..........
            System.out.println(pl1Name);
            System.out.println(pl2Name);
            diceSeq.add(imgblue);
            diceSeq.add(imggreen);
            diceSeq.add(imgred);
            diceSeq.add(imgyel);
            //......mapping token to dice........
            tokenColor.put(imgblue,blueToken);
            tokenColor.put(imggreen,greenToken);
            tokenColor.put(imgred,redToken);
            tokenColor.put(imgyel,yellowToken);


            //........mapping dice with colour..........
            diceColour.put("Blue",imgblue);

            diceColour.put("Green",imggreen);
            diceColour.put("Red",imgred);
            diceColour.put("Yellow",imgyel);
            //......mapping player to colour............
            playerColour.put(pl1Name,pl1Color);
            playerColour.put(pl2Name,pl2Color);
            playerColour.put(pl3Name,pl3Color);
            playerColour.put(pl4Name,pl4Color);
            //.........mapping player to dice..........
            playerDice.put(diceColour.get(playerColour.get(pl1Name)),pl1Name);
            playerDice.put(diceColour.get(playerColour.get(pl2Name)),pl2Name);
            playerDice.put(diceColour.get(playerColour.get(pl3Name)),pl3Name);
            playerDice.put(diceColour.get(playerColour.get(pl4Name)),pl4Name);
            System.out.println(playerDice.get(imgblue));
            System.out.println(playerDice.get(imgred));

            //.....setting player name at dice position.........

            TextView pl1=findViewById(R.id.pl1);

            pl1.setText(playerDice.get(imgblue));
            TextView pl2=findViewById(R.id.pl2);
            pl2.setText(playerDice.get(imggreen));
            TextView pl3=findViewById(R.id.pl3);
            pl3.setText(playerDice.get(imgred));
            TextView pl4=findViewById(R.id.pl4);
            pl4.setText(playerDice.get(imgyel));
            //mapping text view to dice...........
            diceText.put(imgblue,pl1);
            diceText.put(imggreen,pl2);
            diceText.put(imgred,pl3);
            diceText.put(imgyel,pl4);

        }
        public void setDiceVisibility(){
            // if any name is not entered then dice will be not shown at that color........
            for(int i=0;i<4;i++) {
                if (playerDice.get(diceSeq.get(i)) == null) {
                    diceSeq.get(i).setVisibility(View.INVISIBLE);
                }
            }
            //....changing the sequence according to no of players......
            if(playerDice.get(imgblue)==null) diceSeq.remove(imgblue);
            if(playerDice.get(imggreen)==null) diceSeq.remove(imggreen);
            if(playerDice.get(imgred)==null) diceSeq.remove(imgred);
            if(playerDice.get(imgyel)==null) diceSeq.remove(imgyel);

            for(int i=1;i<diceSeq.size();i++){
                ImageView imgv=diceSeq.get(i);



                imgv.setClickable(false);
            }


            TextView start=findViewById(R.id.pl1);
            start.setBackgroundColor(Color.parseColor("#C935C554"));
        }
        public void incrementChance() {
ImageView prevDice=null;
ImageView nextDice=null;
            if (dice_output == 6) return;
            else {


                if(turn%2==0) {
                     prevDice = diceSeq.get((turn-1) % diceSeq.size());


                    nextDice = diceSeq.get(turn % diceSeq.size());
                    turn += 1;
                    System.out.println("turn human " + turn);
                    prevDice.setClickable(false);
                    diceText.get(prevDice).setBackgroundColor(Color.parseColor("#00000000"));
                    nextDice.setClickable(true);
                    diceText.get(nextDice).setBackgroundColor(Color.parseColor("#C935C554"));
                }
                else{
                    if(!humanturn){
                        prevDice = diceSeq.get((turn-1) % diceSeq.size());


                        nextDice = diceSeq.get(turn % diceSeq.size());
                        turn += 1;
                        System.out.println("turn computer " + turn);
                        prevDice.setClickable(false);
                        diceText.get(prevDice).setBackgroundColor(Color.parseColor("#00000000"));
                        nextDice.setClickable(true);
                        diceText.get(nextDice).setBackgroundColor(Color.parseColor("#C935C554"));
                    }

                }
                if (nextDice!=null&&prevDice!=null&&playerDice.get(nextDice).equals("Computer")) {

                    if(!humanturn) {

                        rolle(nextDice);
                    }


                }
            }
            }


            class setPath extends AsyncTask<Void, Void, Void> {


                @Override
                protected Void doInBackground(Void... voids) {
                    //safepoints arraylist  //4april
                    safepoints.add(findViewById(R.id.im9));
                    safepoints.add(findViewById(R.id.if13));
                    safepoints.add(findViewById(R.id.ih3));
                    safepoints.add(findViewById(R.id.ib7));
                    iniSafePos.add(findViewById(R.id.if2));
                    iniSafePos.add(findViewById(R.id.ia9));
                    iniSafePos.add(findViewById(R.id.ih14));
                    iniSafePos.add(findViewById(R.id.in7));
                    int[] blueId = {(R.id.if2), (R.id.if3), (R.id.if4), (R.id.if5), (R.id.if6), (R.id.ie7), (R.id.id7), (R.id.ic7), (R.id.ib7),
                            (R.id.ia7), (R.id.i7), (R.id.i8), (R.id.i9), (R.id.ia9), (R.id.ib9), (R.id.ic9), (R.id.id9), (R.id.ie9), (R.id.if10), (R.id.if11), (R.id.if12)
                            , (R.id.if13), (R.id.if14), (R.id.if15), (R.id.ig15), (R.id.ih15), (R.id.ih14), (R.id.ih13), (R.id.ih12), (R.id.ih11), (R.id.ih10),
                            (R.id.ij9), (R.id.ik9), (R.id.il9), (R.id.im9), (R.id.in9), (R.id.io9), (R.id.io8), (R.id.io7), (R.id.in7), (R.id.im7), (R.id.il7),
                            (R.id.ik7), (R.id.ij7), (R.id.ih6), (R.id.ih5), (R.id.ih4), (R.id.ih3), (R.id.ih2), (R.id.ih1), (R.id.ig1), (R.id.ig2), (R.id.ig3), (R.id.ig4),
                            (R.id.ig5), (R.id.ig6), (R.id.ig7)};
                    for (int i = 0; i < 57; i++) {
                        bluePath.add(findViewById(blueId[i]));
                    }
                    int[] greenId = {(R.id.ia9), (R.id.ib9), (R.id.ic9), (R.id.id9), (R.id.ie9), (R.id.if10), (R.id.if11), (R.id.if12)
                            , (R.id.if13), (R.id.if14), (R.id.if15), (R.id.ig15), (R.id.ih15), (R.id.ih14), (R.id.ih13), (R.id.ih12), (R.id.ih11), (R.id.ih10),
                            (R.id.ij9), (R.id.ik9), (R.id.il9), (R.id.im9), (R.id.in9), (R.id.io9), (R.id.io8), (R.id.io7), (R.id.in7), (R.id.im7), (R.id.il7),
                            (R.id.ik7), (R.id.ij7), (R.id.ih6), (R.id.ih5), (R.id.ih4), (R.id.ih3), (R.id.ih2), (R.id.ih1), (R.id.ig1), (R.id.if1), (R.id.if2), (R.id.if3),
                            (R.id.if4), (R.id.if5), (R.id.if6), (R.id.ie7), (R.id.id7), (R.id.ic7), (R.id.ib7), (R.id.ia7), (R.id.i7), (R.id.i8), (R.id.ia8),
                            (R.id.ib8), (R.id.ic8), (R.id.id8), (R.id.ie8), (R.id.if8)};

                    for (int i = 0; i < 57; i++) {
                        greenPath.add(findViewById(greenId[i]));
                    }
                    //..............Red Path.......................................................//
                    int[] redId = {(R.id.ih14), (R.id.ih13), (R.id.ih12), (R.id.ih11), (R.id.ih10),
                            (R.id.ij9), (R.id.ik9), (R.id.il9), (R.id.im9), (R.id.in9), (R.id.io9), (R.id.io8), (R.id.io7), (R.id.in7), (R.id.im7), (R.id.il7),
                            (R.id.ik7), (R.id.ij7), (R.id.ih6), (R.id.ih5), (R.id.ih4), (R.id.ih3), (R.id.ih2), (R.id.ih1), (R.id.ig1), (R.id.if1), (R.id.if2), (R.id.if3),
                            (R.id.if4), (R.id.if5), (R.id.if6), (R.id.ie7), (R.id.id7), (R.id.ic7), (R.id.ib7), (R.id.ia7), (R.id.i7), (R.id.i8), (R.id.i9), (R.id.ia9),
                            (R.id.ib9), (R.id.ic9), (R.id.id9), (R.id.ie9), (R.id.if10), (R.id.if11), (R.id.if12), (R.id.if13), (R.id.if14), (R.id.if15), (R.id.ig15), (R.id.ig14),
                            (R.id.ig13), (R.id.ig12), (R.id.ig11), (R.id.ig10), (R.id.ig9)};

                    for (int i = 0; i < 57; i++) {
                        redPath.add(findViewById(redId[i]));
                    }
                    //.............Yellow Path.....................................................//
                    int[] yellowId = {(R.id.in7), (R.id.im7), (R.id.il7),
                            (R.id.ik7), (R.id.ij7), (R.id.ih6), (R.id.ih5), (R.id.ih4), (R.id.ih3), (R.id.ih2), (R.id.ih1), (R.id.ig1), (R.id.if1), (R.id.if2), (R.id.if3),
                            (R.id.if4), (R.id.if5), (R.id.if6), (R.id.ie7), (R.id.id7), (R.id.ic7), (R.id.ib7), (R.id.ia7), (R.id.i7), (R.id.i8), (R.id.i9), (R.id.ia9),
                            (R.id.ib9), (R.id.ic9), (R.id.id9), (R.id.ie9), (R.id.if10), (R.id.if11), (R.id.if12), (R.id.if13), (R.id.if14), (R.id.if15), (R.id.ig15), (R.id.ih15),
                            (R.id.ih14), (R.id.ih13), (R.id.ih12), (R.id.ih11), (R.id.ih10), (R.id.ij9), (R.id.ik9), (R.id.il9), (R.id.im9), (R.id.in9), (R.id.io9),
                            (R.id.io8), (R.id.in8), (R.id.im8), (R.id.ie8), (R.id.ik8), (R.id.ij8), (R.id.ih8)};


                    for (int i = 0; i < 57; i++) {

                        yellowPath.add(findViewById(yellowId[i]));
                    }
                    return null;
                }
            }
            class setToPosition extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {


                    blueToken.add(findViewById(R.id.b1));
                    blueToken.add(findViewById(R.id.b2));
                    blueToken.add(findViewById(R.id.b3));
                    blueToken.add(findViewById(R.id.b4));

                    yellowToken.add(findViewById(R.id.y1));
                    yellowToken.add(findViewById(R.id.y2));
                    yellowToken.add(findViewById(R.id.y3));
                    yellowToken.add(findViewById(R.id.y4));

                    greenToken.add(findViewById(R.id.g1));
                    greenToken.add(findViewById(R.id.g2));
                    greenToken.add(findViewById(R.id.g3));
                    greenToken.add(findViewById(R.id.g4));

                    redToken.add(findViewById(R.id.r1));
                    redToken.add(findViewById(R.id.r2));
                    redToken.add(findViewById(R.id.r3));
                    redToken.add(findViewById(R.id.r4));


                    //.....adding blue home views......
                    blueHome.add(findViewById(R.id.ib3));
                    isBlueHome.add(true);
                    blueHome.add(findViewById(R.id.ib4));
                    isBlueHome.add(true);
                    blueHome.add(findViewById(R.id.ic3));
                    isBlueHome.add(true);
                    blueHome.add(findViewById(R.id.ic4));
                    isBlueHome.add(true);
                    //.....adding green home views.........
                    greenHome.add(findViewById(R.id.ib12));
                    isGreenHome.add(true);
                    greenHome.add(findViewById(R.id.ib13));
                    isGreenHome.add(true);
                    greenHome.add(findViewById(R.id.ic12));
                    isGreenHome.add(true);
                    greenHome.add(findViewById(R.id.ic13));
                    isGreenHome.add(true);
                    //.......adding red home views.........
                    redHome.add(findViewById(R.id.il12));
                    isRedHome.add(true);
                    redHome.add(findViewById(R.id.il13));
                    isRedHome.add(true);
                    redHome.add(findViewById(R.id.im12));
                    isRedHome.add(true);
                    redHome.add(findViewById(R.id.im13));
                    isRedHome.add(true);
                    //.......adding yellow home views.........
                    yellowHome.add(findViewById(R.id.il3));
                    isYellowHome.add(true);
                    yellowHome.add(findViewById(R.id.il4));
                    isYellowHome.add(true);
                    yellowHome.add(findViewById(R.id.im3));
                    isYellowHome.add(true);
                    yellowHome.add(findViewById(R.id.im4));
                    isYellowHome.add(true);


                    //...........placing blue token............

                    for (int i = 0; i < 4; i++) {
                        ViewGroup vGroup = (ViewGroup) blueToken.get(i).getParent();
                        vGroup.removeView(blueToken.get(i));
                        if (playerDice.get(imgblue) != null)
                            blueHome.get(i).addView(blueToken.get(i));
                    }


                    //...........placing green token............

                    for (int i = 0; i < 4; i++) {
                        ViewGroup vGroup = (ViewGroup) greenToken.get(i).getParent();
                        vGroup.removeView(greenToken.get(i));
                        if (playerDice.get(imggreen) != null)
                            greenHome.get(i).addView(greenToken.get(i));
                    }

                    //...........placing red token............

                    for (int i = 0; i < 4; i++) {
                        ViewGroup vGroup = (ViewGroup) redToken.get(i).getParent();
                        vGroup.removeView(redToken.get(i));
                        if (playerDice.get(imgred) != null)
                            redHome.get(i).addView(redToken.get(i));
                    }

                    //...........placing yellow token............

                    for (int i = 0; i < 4; i++) {
                        ViewGroup vGroup = (ViewGroup) yellowToken.get(i).getParent();
                        vGroup.removeView(yellowToken.get(i));
                        if (playerDice.get(imgyel) != null)
                            yellowHome.get(i).addView(yellowToken.get(i));
                    }

                    return null;
                }
            }
        }








