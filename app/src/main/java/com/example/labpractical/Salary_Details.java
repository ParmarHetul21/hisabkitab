package com.example.labpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;


public class Salary_Details extends AppCompatActivity {


    EditText edt1,edt2;
    SQLiteDatabase sqLiteDatabase;
    Button button;
    String title;
    int amount;
    int id;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary__details);
        sqLiteDatabase = openOrCreateDatabase("HisabKitab",MODE_PRIVATE,null);
        edt1 = findViewById(R.id.edttitle);
        edt2 = findViewById(R.id.amt);
        button = findViewById(R.id.usalary);
        Intent intent=getIntent();
        userid = intent.getIntExtra("sid",0);


        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM tbl_user WHERE userid='"+userid+"'",null);

        if (c.moveToFirst())
        {
            do {
                 edt1.setText(c.getString(10));
                 edt2.setText(""+c.getInt(3));
            }
            while (c.moveToNext());
        }



        id = fetchid();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = edt1.getText().toString();
                amount = Integer.parseInt(edt2.getText().toString());
                sqLiteDatabase.execSQL("UPDATE tbl_user SET title='"+title+"',salary_amount="+amount+",used_salary="+amount+" WHERE userid="+id+"");
                Toasty.success(Salary_Details.this, "Salary Added Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Salary_Details.this,Home.class);
                intent.putExtra("userid",id);
                startActivity(intent);
                finish();
            }
        });

    }
    public int fetchid()
    {
        Cursor cu = sqLiteDatabase.rawQuery("SELECT * FROM tbl_user",null);
        cu.moveToLast();
        int n = cu.getInt(0);
        return  n;
    }
}
