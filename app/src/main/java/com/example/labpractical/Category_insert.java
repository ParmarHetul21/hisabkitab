package com.example.labpractical;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Category_insert extends AppCompatActivity {

    EditText view;
    Button button;
    SQLiteDatabase db;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_insert);

        view = findViewById(R.id.name);
        button = findViewById(R.id.insert);





//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name = view.getText().toString();
//                Toast.makeText(Category_insert.this, ""+name, Toast.LENGTH_SHORT).show();
//                db.execSQL("INSERT INTO CATEGORY(CTITLE) VALUES('"+name+"');");
//                Toast.makeText(Category_insert.this, "Category Inserted", Toast.LENGTH_SHORT).show();
//            }
//        });

    }



}
