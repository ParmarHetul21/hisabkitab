package com.example.labpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class Personal_Details extends AppCompatActivity {

    int userid;
    DatePickerDialog dialog;
    EditText edt1,edt2,edt3,edt4,edt5;
    SQLiteDatabase database;
    RadioGroup group;
    Button button;
    String name,email,password,dob;
     String number;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__details);
        Intent  intent = getIntent();
        userid = intent.getIntExtra("pid",0);

        edt1 = findViewById(R.id.name);
        edt2 = findViewById(R.id.pemail);
        edt3 = findViewById(R.id.passwod1);
        edt4 = findViewById(R.id.phnumber);
        edt5 = findViewById(R.id.dat);
        group = findViewById(R.id.radioGroup);
        button = findViewById(R.id.UpdateProfile);

        database = openOrCreateDatabase("HisabKitab", MODE_PRIVATE, null);
        Cursor c = database.rawQuery("SELECT * FROM tbl_user WHERE userid = userid",null);
        if (c.moveToFirst())
        {
            do {
                name = c.getString(7);
                email = c.getString(2);
                password = c.getString(1);
                number = c.getString(8);
                dob = c.getString(6);
                edt1.setText(name);
                edt2.setText(email);
                edt3.setText(password);
                edt4.setText(number);
                edt5.setText(dob);

//                Toast.makeText(this, ""+number, Toast.LENGTH_SHORT).show();
            }
            while (c.moveToNext());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt1.getText().toString();
                String email = edt2.getText().toString();
                String password = edt3.getText().toString();
                String number = edt4.getText().toString();

                database.execSQL("UPDATE tbl_user SET fullname='"+name+"',email='"+email+"',password='"+password+"',mobile="+number+" WHERE userid='"+userid+"'");
                Toasty.success(Personal_Details.this, "Personal Details Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Personal_Details.this,Home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
