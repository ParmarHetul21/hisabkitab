package com.example.labpractical;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity {

    PieChart chart;
    String moname;
    FloatingActionButton fab;
    FloatingActionButton fabadd;
    FloatingActionButton fablist;
    FloatingActionButton fabpro;
    FloatingActionButton fabset;
    FloatingActionButton fabcat;
    TextView ae;
    TextView ve;
    TextView pro;
    TextView cat;
    TextView set;
    boolean isFABOpen;
    Spinner spinner;
    int id;
    SQLiteDatabase sqLiteDatabase;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else
        {
//            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        else
        {
//            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
        spinner = findViewById(R.id.customSpinner);
        Calendar c = Calendar.getInstance();
        int no = c.get(Calendar.MONTH);
        ArrayAdapter<CharSequence> sequenceArrayAdapter = ArrayAdapter.createFromResource(this,R.array.month_name,android.R.layout.simple_spinner_item);
        sequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sequenceArrayAdapter);
        spinner.setSelection(no);
        if(spinner != null)
        {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    moname = (String) parent.getSelectedItem();
                    // Action
                    int[] colorArray = new int[]{getResources().getColor(R.color.purple),getResources().getColor(R.color.peach),getResources().getColor(R.color.green),getResources().getColor(R.color.orange),getResources().getColor(R.color.colorPrimaryDark),getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.peach),getResources().getColor(R.color.green),getResources().getColor(R.color.orange),getResources().getColor(R.color.colorPrimaryDark),getResources().getColor(R.color.colorPrimary)};
                    chart = findViewById(R.id.piechart);
                    //PieChart Design
                    PieDataSet pieDataSet = new PieDataSet( entries(moname),"" );
                    pieDataSet.setColors(colorArray);
                    pieDataSet.setSliceSpace(3f);
                    pieDataSet.setValueTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    pieDataSet.setValueTextSize(20);
                    pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    pieDataSet.setSelectionShift(75);

                    PieData pieData = new PieData(pieDataSet);

                    chart.setData(pieData);
                    chart.invalidate();
                    //Pie Chart Design
                    chart.setHoleRadius(80);
                    chart.setUsePercentValues(false);
                    chart.setCenterText("");
                    chart.setCenterTextSize(30);
                    chart.setCenterTextColor(getResources().getColor(R.color.colorPrimary));
                    chart.setDrawEntryLabels(true);
                    chart.setEntryLabelTextSize(20);
                    chart.setEntryLabelColor(getResources().getColor(R.color.colorPrimaryDark));
                    chart.setTransparentCircleRadius(85);
                    chart.setTransparentCircleColor(getResources().getColor(R.color.colorAccent));
                    chart.setTransparentCircleAlpha(80);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        }

        sqLiteDatabase = openOrCreateDatabase("HisabKitab",MODE_PRIVATE,null);
        Cursor cu = sqLiteDatabase.rawQuery("SELECT * FROM tbl_user",null);
        cu.moveToLast();
        int n = cu.getInt(0);
        int tamt = cu.getInt(3);
        sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("userid",n);
        editor.commit();
        Calendar calendar = Calendar.getInstance();
        Date Todaydate = calendar.getTime();
        calendar.add( Calendar.MONTH,1);
        Date fdate = calendar.getTime();
        if(Todaydate == fdate) {
            int userid = sharedPreferences.getInt("userid",0);
            sqLiteDatabase.execSQL( "UPDATE tbl_user SET used_salary="+tamt+" WHERE userid="+userid+"" );
            Toast.makeText( this, "Monthly Salary has been Updated", Toast.LENGTH_SHORT ).show();
        }
        else {
        }

        //PieChart Color


        //Floating Button Menu
        fab = (FloatingActionButton) findViewById(R.id.fabmenu);
        fabadd = (FloatingActionButton) findViewById(R.id.fabadd);
        fablist = (FloatingActionButton) findViewById(R.id.fablist);
        fabpro = (FloatingActionButton) findViewById(R.id.fabpro);
        fabset = (FloatingActionButton) findViewById(R.id.fabset);
        fabcat = (FloatingActionButton) findViewById(R.id.fabcat);
        ae = (TextView) findViewById(R.id.AddExpense);
        ve = (TextView) findViewById(R.id.ViewExpenses);
        pro = (TextView) findViewById(R.id.Profile);
        cat = (TextView) findViewById(R.id.ViewCategory);
        set = (TextView) findViewById(R.id.Settings);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
            @SuppressLint("RestrictedApi")
            private void showFABMenu(){
                isFABOpen=true;
                fabadd.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
                fablist.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
                fabpro.animate().translationY(-getResources().getDimension(R.dimen.standard_205));
                fabset.animate().translationY(-getResources().getDimension(R.dimen.standard_255));
                fabcat.animate().translationY(-getResources().getDimension(R.dimen.standard_155));

                fab.setImageResource(R.drawable.ic_menu_50dp);

                fabadd.setVisibility(View.INVISIBLE);
                fablist.setVisibility(View.INVISIBLE);
                fabpro.setVisibility(View.INVISIBLE);
                fabset.setVisibility(View.INVISIBLE);
                fabcat.setVisibility(View.INVISIBLE);

                ae.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
                ve.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
                pro.animate().translationY(-getResources().getDimension(R.dimen.standard_205));
                set.animate().translationY(-getResources().getDimension(R.dimen.standard_255));
                cat.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
                ae.setVisibility(View.INVISIBLE);
                ve.setVisibility(View.INVISIBLE);
                pro.setVisibility(View.INVISIBLE);
                set.setVisibility(View.INVISIBLE);
                cat.setVisibility(View.INVISIBLE);

            }

            @SuppressLint("RestrictedApi")
            private void closeFABMenu(){
                isFABOpen=false;
                fabadd.animate().translationY(0);
                fablist.animate().translationY(0);
                fabpro.animate().translationY(0);
                fabset.animate().translationY(0);
                fabcat.animate().translationY(0);

                fab.setImageResource(R.drawable.ic_close_50dp);

                fabadd.setVisibility(View.VISIBLE);
                fablist.setVisibility(View.VISIBLE);
                fabpro.setVisibility(View.VISIBLE);
                fabset.setVisibility(View.VISIBLE);
                fabcat.setVisibility(View.VISIBLE);

                ae.animate().translationY(0);
                ve.animate().translationY(0);
                pro.animate().translationY(0);
                set.animate().translationY(0);
                cat.animate().translationY(0);
                ae.setVisibility(View.VISIBLE);
                ve.setVisibility(View.VISIBLE);
                pro.setVisibility(View.VISIBLE);
                set.setVisibility(View.VISIBLE);
                cat.setVisibility(View.VISIBLE);

            }

        });
        //PieChart Data

        // Toast.makeText(this, ""+amt, Toast.LENGTH_SHORT).show();
    }
    //Pie Chart Array
    // value=useamt and lable=category
    private ArrayList<PieEntry> entries(String moname)
    {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        int pieid = sharedPreferences.getInt("userid",0);

        Cursor cur = sqLiteDatabase.rawQuery("SELECT DISTINCT(tbl_expense.cid), CATEGORY.ctitle FROM tbl_expense, CATEGORY WHERE CATEGORY.CID=tbl_expense.cid AND tbl_expense.userid="+pieid+" AND month='"+moname+"' ",null);
        cur.moveToFirst();
    if(cur.getCount() > 0){
        do{
            int id = cur.getInt(0);
            String title = cur.getString(1);

            Cursor cursorpie = sqLiteDatabase.rawQuery("SELECT SUM(useamt) FROM tbl_expense WHERE cid="+id+" AND userid="+pieid,null);
            cursorpie.moveToFirst();

            int amt = cursorpie.getInt(0);
            pieEntries.add(new PieEntry(amt, title));

        } while (cur.moveToNext());

        }
        else
        {
            Cursor sal = sqLiteDatabase.rawQuery("Select * from tbl_user",null);
            sal.moveToFirst();
            int salary = sal.getInt(3);
            pieEntries.add(new PieEntry(salary,"Salary"));
        }
        return pieEntries;
    }

    public void gotoprofile(View view) {
        Intent intent = new Intent(this,Profile.class);
        intent.putExtra("userid",n);
        startActivity(intent);
    }

    public void viewcategory(View view) {
        Intent intent = new Intent(Home.this,View_Category.class);
        startActivity(intent);

    }

    public void addexpense(View view) {
        Intent intent = new Intent(Home.this,Add_Expense.class);
        startActivity(intent);

    }

    public void viewexpense(View view) {
        Intent intent = new Intent(Home.this,View_Expenses.class);
        startActivity(intent);

    }
}