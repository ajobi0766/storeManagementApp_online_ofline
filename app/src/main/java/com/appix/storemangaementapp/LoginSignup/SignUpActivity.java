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
import android.widget.Toast;

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

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity {
    Constants util;
    private EditText ed_name,ed_email,ed_password;
    private TextView tv_login;
    private Button btn_signup;
     String name,email,password;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressBar signup_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        util = new Constants(this);
        preferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        editor = preferences.edit();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        ed_name = (EditText)findViewById(R.id.signup_name);
        ed_email = (EditText)findViewById(R.id.signup_email);
        ed_password = (EditText)findViewById(R.id.signup_password);
        tv_login = (TextView) findViewById(R.id.tv_login);
        btn_signup = (Button)findViewById(R.id.btn_signup);
        signup_progress = (ProgressBar)findViewById(R.id.signup_progress);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = ed_name.getText().toString();
                email = ed_email.getText().toString();
                password = ed_password.getText().toString();

                if(TextUtils.isEmpty(name)){
                    ed_name.setError("نام درج کریں");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    ed_email.setError("ای میل درج کریں");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    ed_password.setError("پاس ورڈ درج کریں");
                    return;
                }
                signup_progress.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String userId = mAuth.getCurrentUser().getUid();
                            UserModel model = new UserModel(userId,name,email);
                            myRef.child("AllUsers").child(userId).setValue(model);
                            editor.putString("email",email);
                            editor.commit();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                         //   signup_progress.setVisibility(View.VISIBLE);

                        }else{
                            ed_email.setError("ای میل کے لئے غلط فارمیٹ");
                            signup_progress.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
        });
    }
}