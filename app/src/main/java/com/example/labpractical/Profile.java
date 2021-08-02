package com.example.labpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    int id;
    SQLiteDatabase database;
    int nm;
    TextView tv1, tv2;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        nm = sharedPreferences.getInt("userid",0);
        tv1 = findViewById(R.id.pname);
        tv2 = findViewById(R.id.money);

        database = openOrCreateDatabase("HisabKitab", MODE_PRIVATE, null);
        Cursor c = database.rawQuery("SELECT * FROM tbl_user WHERE userid = '" + nm + "'", null);

        if (c.moveToFirst())
        {
            do {
                String name = c.getString(7);
                int salary = c.getInt(4);
                tv1.setText(name);
                tv2.setText(""+salary);
            }
            while (c.moveToNext());
        }
    }

    public void personal_details(View view) {
        id = sharedPreferences.getInt("userid",0);
        Intent intent = new Intent(this, Personal_Details.class);
        intent.putExtra("pid",id);
        startActivity(intent);
        finish();
    }

    public void salary_details(View view) {
        id = sharedPreferences.getInt("userid",0);
        Intent intent = new Intent(this, Salary_Details.class);
        intent.putExtra("sid",id);
        startActivity(intent);
        finish();
    }

    public void manage_account(View view) {
        id = sharedPreferences.getInt("userid",0);
        Intent intent = new Intent(this, Manage_Account.class);
        startActivity(intent);
        finish();
    }

    public void logout(View view) {
        sharedPreferences.edit().remove("Email").remove("userid").commit();
        Intent intent  = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
