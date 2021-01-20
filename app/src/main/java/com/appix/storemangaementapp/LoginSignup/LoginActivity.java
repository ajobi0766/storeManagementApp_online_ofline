package com.appix.storemangaementapp.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appix.storemangaementapp.Constants;
import com.appix.storemangaementapp.MainActivity;
import com.appix.storemangaementapp.Model.UserModel;
import com.appix.storemangaementapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    Constants util;
    private EditText ed_email,ed_password;
    private TextView tv_signup;
    private Button btn_login;
    String name,email,password;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressBar login_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        util = new Constants(this);
        preferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        editor = preferences.edit();
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");


        ed_email = (EditText)findViewById(R.id.login_email);
        ed_password = (EditText)findViewById(R.id.login_password);
        tv_signup = (TextView)findViewById(R.id.tv_signup);
        btn_login = (Button)findViewById(R.id.btn_login);
        login_progress = (ProgressBar)findViewById(R.id.login_progress);

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = ed_email.getText().toString();
                password = ed_password.getText().toString();

                if(TextUtils.isEmpty(email)){
                    ed_email.setError("ای میل درج کریں");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    ed_password.setError("پاس ورڈ درج کریں");
                    return;
                }
                login_progress.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            editor.putString("email",email);
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            ed_email.setError("ای میل کے لئے غلط فارمیٹ");
                            login_progress.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });
    }
}
