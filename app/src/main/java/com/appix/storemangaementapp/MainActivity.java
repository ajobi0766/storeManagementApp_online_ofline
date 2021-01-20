package com.appix.storemangaementapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appix.storemangaementapp.LoginSignup.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    LinearLayout add_btn,btn_detail,stock_linear;
    Button save_btn;
    ImageView logout_img;
    Constants constants;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        constants =new Constants(this);


        add_btn=(LinearLayout) findViewById(R.id.add_linear);
        btn_detail=(LinearLayout) findViewById(R.id.list_linear);
        stock_linear=(LinearLayout) findViewById(R.id.stock_linear);
        save_btn=(Button) findViewById(R.id.save_btn);
        logout_img=(ImageView) findViewById(R.id.logout_img);

        preferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        editor = preferences.edit();

        if(constants.isLoggedIn()){
            save_btn.setVisibility(View.GONE);
            logout_img.setVisibility(View.VISIBLE);
        }else{
            logout_img.setVisibility(View.GONE);
            save_btn.setVisibility(View.VISIBLE);
        }

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }
        });
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ShowItems.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }
        });
        stock_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,TotalStockActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

        logout_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withImageView();
//                final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
//                builder1.setMessage("کیا آپ واقعے لاگ آوٹ کرنا چاھتے ہیں!");
//                builder1.setIcon(R.mipmap.ic_launcher);
//                builder1.setCancelable(true);
//                builder1.setPositiveButton(
//                        "جی ہاں",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id_) {
//                                editor.remove("email").apply();
//                                recreate();
//                                dialog.cancel();
//
//                            }
//                        });
//
//                builder1.setNegativeButton(
//                        "نہیں",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                builder1.show();
////                 AlertDialog alert11 = builder1.create();
////                 alert11.show();
            }
        });

    }
   public void  withImageView(){
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       LayoutInflater inflater = getLayoutInflater();
       View dialogLayout = inflater.inflate(R.layout.alert_dialog_with_imageview, null);
       builder.setView(dialogLayout);
       builder.setPositiveButton(
                        "جی ہاں",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id_) {
                                editor.remove("email").apply();
                                recreate();
                                dialog.cancel();

                            }
                        });

                builder.setNegativeButton(
                        "نہیں",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.show();



    }
}