package com.example.labpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import es.dmoral.toasty.Toasty;

public class Add_Expense extends AppCompatActivity{

    Button cancle,done;
    EditText title, desc, amt;
    Spinner customSpinner;
    SQLiteDatabase db;
    String t,d;
    int a,Cid;
    String formatdate;
    int temp;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__expense);
        cancle = (Button) findViewById(R.id.cancle);
        title = (EditText) findViewById(R.id.ExTitle);
        desc = (EditText) findViewById(R.id.exDes);
        amt = findViewById(R.id.ExAmount);
        done = (Button) findViewById(R.id.done);

        db = openOrCreateDatabase("HisabKitab", MODE_PRIVATE, null);
        sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        uid = sharedPreferences.getInt("userid",0);

        customSpinner = findViewById(R.id.spinner2);
        // create spinneritemlist for spinner
        ArrayList<CustomItems> list = new ArrayList<>();
        ArrayList<Integer> array_image = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery("SELECT * FROM CATEGORY", null);
        cursor.moveToFirst();
        array_image.add(R.drawable.bills);
        array_image.add(R.drawable.debt);
        array_image.add(R.drawable.entertenment);
        array_image.add(R.drawable.food);
        array_image.add(R.drawable.fun);
        array_image.add(R.drawable.furniture);
        array_image.add(R.drawable.gifts);
        array_image.add(R.drawable.groseary);
        array_image.add(R.drawable.health);
        array_image.add(R.drawable.insurance);
        array_image.add(R.drawable.investment);
        array_image.add(R.drawable.pat);
        array_image.add(R.drawable.personal);
        array_image.add(R.drawable.rent);
        array_image.add(R.drawable.reparing);
        array_image.add(R.drawable.shopping);
        array_image.add(R.drawable.tax);
        array_image.add(R.drawable.travelling);
        cursor.moveToFirst();
        do {
            CustomItems customItems = new CustomItems();
            customItems.setId(cursor.getInt(0));
            customItems.setSpinnerText(cursor.getString(1));
            list.add(customItems);

        } while (cursor.moveToNext());

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.activity_custom_spinner, list, array_image);
        customSpinner.setAdapter(customAdapter);
        if (customSpinner != null) {
            customSpinner.setAdapter(customAdapter);
            customSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CustomItems items = (CustomItems) parent.getSelectedItem();
                    Cid = items.getId();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });
        }

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.length() == 0 && desc.length() == 0 && amt.length() == 0) {
                    Intent intent = new Intent(Add_Expense.this, Home.class);
                    startActivity(intent);
                } else {
                    title.setText("");
                    desc.setText("");
                    amt.setText("");
                }

            }
        });

         Cursor c = db.rawQuery("SELECT * FROM tbl_user",null);
         c.moveToFirst();

         temp = c.getInt(4);


        //Date
        Date da = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        formatdate = dateFormat.format(da);
        //Month
        Date da1 = Calendar.getInstance().getTime();
        SimpleDateFormat Month = new SimpleDateFormat("MMM");
        final String Month_name = Month.format(da1.getTime());
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().isEmpty()) {
                    Toasty.warning(Add_Expense.this, "Expense Title is Empty", Toast.LENGTH_SHORT).show();
                }
                else if(desc.getText().toString().isEmpty()){
                    Toasty.warning(Add_Expense.this, "Expense Description is Empty", Toast.LENGTH_SHORT).show();
                }
                else if(amt.getText().toString().isEmpty())
                {
                    Toasty.warning(Add_Expense.this, "Expense Amount is Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    t = title.getText().toString();
                    d = desc.getText().toString();
                    a = Integer.parseInt(amt.getText().toString());
                    int value = temp - a;
                    if(a>temp){
                        Toasty.error(Add_Expense.this, "You Don't Have "+a+" Rupees", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        db.execSQL("INSERT INTO tbl_expense(useamt,date_of_expense,description,userid,CID,Exptitle,month) VALUES("+a+",'"+formatdate+"','"+d+"',"+uid+","+Cid+",'"+t+"','"+Month_name+"')");

                        db.execSQL("UPDATE tbl_user SET used_salary="+value+" WHERE userid="+uid+"");
                        Toasty.success(Add_Expense.this,"Expense Added Successfully",Toasty.LENGTH_SHORT).show();
                        Intent intent = new Intent(Add_Expense.this,Home.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
    }
}
