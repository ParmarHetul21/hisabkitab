package com.example.labpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class Register extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText edt1,edt2,edt3,edt4,edt5,edt6;
    DatePickerDialog dialog;
    RadioButton rd1;
    Button button;
    RadioGroup group;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sqLiteDatabase = openOrCreateDatabase("HisabKitab",MODE_PRIVATE,null);
        //Find the variuables
        edt1 = findViewById(R.id.name);
        edt2 = findViewById(R.id.email);
        edt3 = findViewById(R.id.password);
        edt4 = findViewById(R.id.cpassword);
        edt5 = findViewById(R.id.mobile);
        edt6 = findViewById(R.id.dob);
        group = findViewById(R.id.radioGroup);
        button = findViewById(R.id.Register);
        //Settings the Date
        edt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year =  calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                dialog = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edt6.setText(""+dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month+1,day);
                dialog.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioid = group.getCheckedRadioButtonId();
                rd1 =findViewById(radioid);
                String name = edt1.getText().toString();
                String email = edt2.getText().toString();
                String password = edt3.getText().toString();
                String mobile = edt5.getText().toString();
                String dob = edt6.getText().toString();
                String gender = rd1.getText().toString();
                Date d = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formatdate = dateFormat.format(d);

                //category
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Bills');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Debt');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Entertainment');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Food');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Fun');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Furniture & Tools');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Gift & Donation');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Groceries');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Health & Fitness');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Insurance');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Investment ');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Pets');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Personal Care');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Mortggage & Rent');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Repairs');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Shopping');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Taxes');");
                sqLiteDatabase.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('Travel');");

                sqLiteDatabase.execSQL("INSERT INTO tbl_user(fullname,email,password,mobile,date_of_birth,gender,date_of_join) VALUES('"+name+"','"+email+"','"+password+"','"+mobile+"','"+dob+"','"+gender+"','"+formatdate+"')");
                Toasty.success(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                //create Preference
                editor.putString("Email",email);
                editor.commit();

                Intent intent = new Intent(Register.this,Salary_Details.class);
                startActivity(intent);
            }
        });
    }
}
