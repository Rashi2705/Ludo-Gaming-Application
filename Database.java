package com.example.ludo3;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class Database extends SQLiteOpenHelper {

    public static final String PLAYER = "Player";
    private static final String database = "db" + PLAYER + ".db";//creating db
    public static final String COLUMN_USERNAME = "username";
    public static final String PID_column = "Pid";
    public static final String PASSWORD_column = "password";
    public static final String WINS_column = "wins";
    public static final String TOTALPLAYED_column = "totalplayed";

    public Database(@Nullable Context context) {
        super(context, database, null, 1);//db created

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table " + PLAYER + "(" + PID_column + " integer  , auto_increment ," + COLUMN_USERNAME + " text primary key," + PASSWORD_column + " varchar," + WINS_column + " integer," + TOTALPLAYED_column + " integer)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + PLAYER);
        onCreate(sqLiteDatabase);

    }

    public boolean insertrec(USERMODEL um) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues obj = new ContentValues();
        obj.put(COLUMN_USERNAME, um.getUsername());
        obj.put(PASSWORD_column, um.getPassword());
        obj.put(WINS_column, um.getWins());
        obj.put(TOTALPLAYED_column, um.getTotalPlayed());
        long rec = db.insert(PLAYER, null, obj);
        if (rec == -1)
            return false;
        else
            return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean searchrec(String uname, String passw) {
        SQLiteDatabase db = this.getReadableDatabase();
        String unamec = "", passc="";

        Cursor cursor = db.rawQuery("Select " + COLUMN_USERNAME + "," + PASSWORD_column + " from " + PLAYER + " where " + COLUMN_USERNAME + " = ? ", new String[]{uname});
        if (cursor.moveToFirst()) {

            unamec = cursor.getString(0);
            passc = cursor.getString(1);

        }//end of if
        System.out.println(unamec + passc);
        //decode
        byte[] decodedBytes = Base64.getDecoder().decode(passc);
        String decodedpswd = new String(decodedBytes);
        if (unamec.equals(uname) && decodedpswd.equals(passw))
            return true;
        else
            return false;
    }
    public boolean updateplay(Integer win , Integer tplay , String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues obj = new ContentValues();
        Integer win1 = null,tplay1 = null;
        Cursor cursor = db.rawQuery("Select " + WINS_column + "," + TOTALPLAYED_column + " from " + PLAYER + " where " + COLUMN_USERNAME + " = ? ", new String[]{username});
        if (cursor.moveToFirst()) {

            win1 = cursor.getInt(0);
            tplay1 = cursor.getInt(1);

        }//end of if

        System.out.println(win1 );
        System.out.println(tplay1 );
        win1 = win1+win;
        tplay1 = tplay+tplay1;
        obj.put(TOTALPLAYED_column,tplay1);
        obj.put(WINS_column, win1);

        long upd = db.update(PLAYER,obj, COLUMN_USERNAME + " = ? ",new String[]{username});
        System.out.println(win1 );
        System.out.println(tplay1 );
        if(upd == -1)
            return false;
        else
            return  true;
    }
    public boolean reset(String uname ,String newpswd)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues obj = new ContentValues();
        obj.put(PASSWORD_column, newpswd);
        long upd = db.update(PLAYER,obj, COLUMN_USERNAME + " = ? ",new String[]{uname});
        if(upd == -1)
            return false;
        else
            return  true;


    }
    public boolean resetuname(String uname ,String newunmae)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues obj = new ContentValues();
        obj.put(COLUMN_USERNAME, newunmae);
        long upd = db.update(PLAYER,obj, COLUMN_USERNAME + " = ? ",new String[]{uname});

        if(upd == -1)
            return false;
        else
            return  true;


    }
    public int getwin( String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        Integer win1 = null;
        Cursor cursor = db.rawQuery("Select " + WINS_column +  " from " + PLAYER + " where " + COLUMN_USERNAME + " = ? ", new String[]{username});
        if (cursor.moveToFirst()) {

            win1 = cursor.getInt(0);


        }//end of if

        System.out.println(win1 );


        return win1;
    }
    public int getplay( String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        Integer tplay1 = null;
        Cursor cursor = db.rawQuery("Select " +  TOTALPLAYED_column + " from " + PLAYER + " where " + COLUMN_USERNAME + " = ? ", new String[]{username});
        if (cursor.moveToFirst()) {


            tplay1 = cursor.getInt(0);

        }//end of if


        System.out.println(tplay1 );

        return tplay1;
    }


}