package com.example.labpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class View_Category extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__category);

        db = openOrCreateDatabase("HisabKitab",MODE_PRIVATE,null);
        listView = findViewById(R.id.mylist);

        final ArrayList<MyDataItem> list = new ArrayList<>();

        Cursor cursor =db.rawQuery("SELECT * FROM CATEGORY",null);

        ArrayList<Integer> array_image = new ArrayList<Integer>();

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
        do{
            MyDataItem myDataItem = new MyDataItem();
            myDataItem.setId(cursor.getInt(0));
            myDataItem.setName(cursor.getString(1));
            list.add(myDataItem);

        }while (cursor.moveToNext());


        MyArrayAdapter myArrayAdapter = new MyArrayAdapter(View_Category.this,R.layout.activity_list__display,list,array_image);
        listView.setAdapter(myArrayAdapter);
    }
}
