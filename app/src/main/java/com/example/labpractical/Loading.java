package com.example.labpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.animation.content.Content;

import java.util.Timer;
import java.util.TimerTask;

public class Loading extends AppCompatActivity {

    Timer timer;
    SQLiteDatabase sqLiteDatabase;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String e;
    int id;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE);
        sqLiteDatabase = openOrCreateDatabase("HisabKitab",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbl_user(userid INTEGER PRIMARY KEY AUTOINCREMENT,password TEXT,email TEXT,salary_amount INT,used_salary INT,date_of_join DATE,date_of_birth DATE,fullname TEXT,mobile INT,gender TEXT,title TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS CATEGORY(CID INTEGER PRIMARY KEY AUTOINCREMENT,CTITLE TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbl_expense(eid INTEGER PRIMARY KEY AUTOINCREMENT,useamt INTEGER,date_of_expense DATE,description TEXT,userid INTEGER,CID INTEGER,Exptitle TEXT,month TEXT,FOREIGN KEY (userid) REFERENCES tbl_user (userid), FOREIGN KEY (CID) REFERENCES CATEGORY(CID));");


        editor = sharedPreferences.edit();

        e = sharedPreferences.getString("Email","NOT_SET");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            if(sharedPreferences.contains("Email")){
                Log.d("HHH","Contains ");
                Intent mIntent = new Intent(Loading.this,Home.class);
                mIntent.putExtra("id",id);
                startActivity(mIntent);
                finish();
            }
            else
            {
                Log.d("HHH","Do Not Contain ");

                startActivity(new Intent(Loading.this,MainActivity.class));
                finish();
            }
              finish();
            }
        },4000);
  }
}
