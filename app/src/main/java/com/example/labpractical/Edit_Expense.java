package com.example.labpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class Edit_Expense extends AppCompatActivity {

    String formatdate;
    int temp;
    int uid;
    Button done,delete;
    String t,d,da;
    int newexpamt,prevexpamt;
    EditText title, desc, amt,dobup;
    SQLiteDatabase database;
    Spinner spinner;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int exid;
    int Cid;
    int updexp;
    int diff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__expense);

        sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE);
        uid = sharedPreferences.getInt("userid",0);
        database = openOrCreateDatabase("HisabKitab",MODE_PRIVATE,null);

        delete = (Button) findViewById(R.id.expdelete);
         done = (Button) findViewById(R.id.expupdate);
        title = (EditText) findViewById(R.id.ExTitleup);
        desc = (EditText) findViewById(R.id.exDesup);
        amt = (EditText) findViewById(R.id.ExAmountup);
        spinner = (Spinner) findViewById(R.id.spinnerup);
        dobup = (EditText) findViewById(R.id.dobup);
        Intent intent = getIntent();

        exid = intent.getIntExtra("eid",0);

        Toast.makeText(this, ""+exid, Toast.LENGTH_SHORT).show();


        ArrayList<CustomItems> list = new ArrayList<>();
        ArrayList<Integer> array_image = new ArrayList<Integer>();
        Cursor cursor1 = database.rawQuery("SELECT * FROM CATEGORY ", null);
        cursor1.moveToFirst();
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
        cursor1.moveToFirst();
        do {
            CustomItems customItems = new CustomItems();
            customItems.setId(cursor1.getInt(0));
            customItems.setSpinnerText(cursor1.getString(1));
            list.add(customItems);
        } while (cursor1.moveToNext());

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.activity_custom_spinner, list, array_image);
        int a = intent.getIntExtra("Id",0);
        if (spinner != null) {
            spinner.setAdapter(customAdapter);
            spinner.setSelection(a-1);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CustomItems items = (CustomItems) parent.getSelectedItem();
                    Cid = items.getId();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });
        }




        Cursor c = database.rawQuery("SELECT * FROM tbl_user",null);
        c.moveToFirst();

        temp = c.getInt(4);

        Cursor cursor = database.rawQuery("SELECT * FROM tbl_expense WHERE tbl_expense.cid="+a+"",null);
        cursor.moveToNext();

        prevexpamt = cursor.getInt(1);

       title.setText(cursor.getString(6));
        desc.setText(cursor.getString(3));
        amt.setText(""+cursor.getInt(1));
        dobup.setText(cursor.getString(2));




        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t = title.getText().toString();
                d = title.getText().toString();
                da = dobup.getText().toString();
                //User Entered amount

                newexpamt = Integer.parseInt(amt.getText().toString());

                int value = temp - newexpamt;

                if(newexpamt>temp){
                    Toasty.error(Edit_Expense.this, "You Don't Have "+newexpamt+" Rupees", Toast.LENGTH_SHORT).show();
                }
                else if(newexpamt > prevexpamt ){
                    diff = newexpamt - prevexpamt;
                    updexp = temp - diff;
                    int new_rem_salary = temp - diff;
                    database.execSQL("UPDATE tbl_expense SET  Exptitle='"+t+"', description='"+d+"',useamt="+newexpamt+",date_of_expense='"+da+"',CID="+Cid+" WHERE eid="+exid+"");
                    database.execSQL("UPDATE tbl_user SET used_salary="+new_rem_salary+" WHERE userid="+uid+"");
                    Toasty.success(Edit_Expense.this,"Expense Updated Successfully",Toasty.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Edit_Expense.this,Home.class);
                    startActivity(intent1);

                }
                else if(newexpamt < prevexpamt)
                {
                     diff = prevexpamt - newexpamt ;
                     updexp = temp + diff;
                     int new_rem_salary = temp + diff;
                    database.execSQL("UPDATE tbl_expense SET  Exptitle='"+t+"', description='"+d+"',useamt="+newexpamt+",date_of_expense='"+da+"',CID="+Cid+" WHERE eid="+exid+"");
                    database.execSQL("UPDATE tbl_user SET used_salary="+new_rem_salary+" WHERE userid="+uid+"");
                    Toasty.success(Edit_Expense.this,"Expense Updated Successfully",Toasty.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Edit_Expense.this,Home.class);
                    startActivity(intent1);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int new_rem_salary = temp + prevexpamt;
                database.execSQL("UPDATE tbl_user SET used_salary="+new_rem_salary+" WHERE userid="+uid+"");

                database.execSQL("DELETE FROM tbl_expense WHERE eid="+exid+"");

                Toasty.success(Edit_Expense.this,"DELETED SUCESSFULLY",Toasty.LENGTH_SHORT).show();
                Intent intent1 = new Intent(Edit_Expense.this,Home.class);
                startActivity(intent1);
            }
        });

    }

}
