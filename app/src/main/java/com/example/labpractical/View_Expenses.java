package com.example.labpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class View_Expenses extends AppCompatActivity {

    SQLiteDatabase database;
    ListView listView;
    int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__expenses);

        listView = findViewById(R.id.expenseslist);
        database = openOrCreateDatabase("HisabKitab",MODE_PRIVATE,null);

        final ArrayList<ViewExpenseItem> list = new ArrayList<>();
        Cursor cursorexp = database.rawQuery("SELECT * FROM tbl_expense,CATEGORY WHERE tbl_expense.cid=CATEGORY.cid",null);
        cursorexp.moveToFirst();

       do{
            final ViewExpenseItem viewExpenseItem = new ViewExpenseItem();
            viewExpenseItem.setTitle(cursorexp.getString(6));
            Log.d("CCCCCC", "" + cursorexp.getInt(8));
            viewExpenseItem.setEid(cursorexp.getInt(0));
            viewExpenseItem.setId(cursorexp.getInt(8));
            viewExpenseItem.setCtitle(cursorexp.getString(9));
            viewExpenseItem.setDate(cursorexp.getString(2));
            viewExpenseItem.setAmount(cursorexp.getInt(1));
            list.add(viewExpenseItem);
        } while (cursorexp.moveToNext());
        ViewExpenseItemAdapter adapter = new ViewExpenseItemAdapter(this,R.layout.activity_custom__expenses__list,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int exid = list.get(position).getEid();
                int Newid = list.get(position).getId();
                Intent intent = new Intent(View_Expenses.this,Edit_Expense.class);
                intent.putExtra("Id",Newid);
                intent.putExtra("eid",exid);
                startActivity(intent);
            }
        });
    }

    public void addexpense(View view) {
        Intent intent = new Intent(this,Add_Expense.class);
        startActivity(intent);
        finish();
    }
}
