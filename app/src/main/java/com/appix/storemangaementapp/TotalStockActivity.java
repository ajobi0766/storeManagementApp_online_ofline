package com.appix.storemangaementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TotalStockActivity extends AppCompatActivity {
    SQLiteDatabaseHandler db;
    private TextView tv_stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_stock);
        getSupportActionBar().hide();
        tv_stock=(TextView)findViewById(R.id.total_stock);

        db= new SQLiteDatabaseHandler(this);
      float value=  db.getCountriesCount();
        Log.d("",""+value);
        tv_stock.setText(""+value+"\t"+"روپے");

    }
}