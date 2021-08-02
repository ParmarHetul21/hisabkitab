package com.example.labpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Manage_Account extends AppCompatActivity {

    SQLiteDatabase database;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView textView , textView1, textView2;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__account);
        textView = (TextView) findViewById(R.id.ExportExpensesData);
        textView1 = (TextView) findViewById(R.id.DeleteData);
        textView2 = (TextView) findViewById(R.id.DeleteAccount);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = openOrCreateDatabase("HisabKitab", MODE_PRIVATE, null);

                sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE);
                userid = sharedPreferences.getInt("userid",0);

                ArrayList<ViewExpenseItem> list = new ArrayList<>();
                Cursor cursorexplist = database.rawQuery("SELECT * FROM tbl_expense,CATEGORY WHERE tbl_expense.cid=CATEGORY.cid AND tbl_expense.userid = " + userid, null);
                cursorexplist.moveToFirst();
                int count = cursorexplist.getCount();

                File sd = Environment.getExternalStorageDirectory();
                String csvFile = "MyExpList.xls";

                File directory = new File(sd.getAbsolutePath());
                //create directory if not exist
                if (!directory.isDirectory()) {
                    directory.mkdirs();
                }
                try {

                    //file path
                    File file = new File(directory, csvFile);
                    WorkbookSettings wbSettings = new WorkbookSettings();
                    wbSettings.setLocale(new Locale("en", "EN"));
                    WritableWorkbook workbook;
                    workbook = Workbook.createWorkbook(file, wbSettings);
                    //Excel sheet name. 0 represents first sheet
                    WritableSheet sheet = workbook.createSheet("Expense List", 0);
                    // column and row
                    sheet.addCell(new Label(0, 0, "Id"));
                    sheet.addCell(new Label(1, 0, "Expense Amount"));
                    sheet.addCell(new Label(2, 0, "Expense Title"));
                    sheet.addCell(new Label(3, 0, "Description"));
                    sheet.addCell(new Label(4, 0, "Date of Expense"));
                    sheet.addCell(new Label(5, 0, "Expense Category"));

                    if (cursorexplist.moveToFirst()){
                        do {
                            int eid = cursorexplist.getInt(0);
                            int eamt = cursorexplist.getInt(1);
                            String edoe = cursorexplist.getString(6);
                            String ed = cursorexplist.getString(3);
                            String et = cursorexplist.getString(2);
                            String ec = cursorexplist.getString(9);
                            String eids = String.valueOf(eid);
                            String eamts = String.valueOf(eamt);

                            int i = cursorexplist.getPosition() + 1;

                            sheet.addCell(new Label(0, i, eids));
                            sheet.addCell(new Label(1, i, eamts));
                            sheet.addCell(new Label(2, i, edoe));
                            sheet.addCell(new Label(3, i, ed));
                            sheet.addCell(new Label(4, i, et));
                            sheet.addCell(new Label(5, i, ec));
                        } while (cursorexplist.moveToNext());

                        //closing cursor
                        cursorexplist.close();
                        workbook.write();
                        workbook.close();
                    }


                    Toasty.success(Manage_Account.this,"Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();

                } catch (RowsExceededException e) {
                    Toasty.error(Manage_Account.this,""+e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (WriteException e) {
                    Toasty.error(Manage_Account.this,""+e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toasty.error(Manage_Account.this,""+e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        });

    }
}
