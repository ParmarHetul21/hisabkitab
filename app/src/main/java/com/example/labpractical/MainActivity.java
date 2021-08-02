package com.example.labpractical;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    TextView tv1,tv2;
    Button button2;
    String email,password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String e;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("login_pref",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sqLiteDatabase = openOrCreateDatabase("HisabKitab",MODE_PRIVATE,null);
        e = sharedPreferences.getString("Email","NOT_SET");
        tv1 = findViewById(R.id.email);
        tv2 = findViewById(R.id.password);
        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(tv1.getText().toString().isEmpty()) {
                    Toasty.warning(MainActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }
                else if(tv2.getText().toString().isEmpty()) {
                    Toasty.warning(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    email = tv1.getText().toString();
                    password = tv2.getText().toString();
                    c = sqLiteDatabase.rawQuery("SELECT * FROM tbl_user WHERE email='" + email + "' and password='" + password + "' ;", null);
                    c.moveToFirst();

                    if (c.getCount() > 0)
                    {
                        if( e.equals("NOT_SET")) {
                            editor.putString("Email",email);
                            editor.commit();
                            Toasty.success(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Home.class);
                            startActivity(intent);
                        }
                        else{
                            Toasty.success(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Home.class);
                            startActivity(intent);
                        }
                    }
                    else {

                        tv1.setText("");
                        tv2.setText("");
                        tv1.requestFocus();
                        Toasty.error(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void Transfer(View view) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
        finish();
    }

}