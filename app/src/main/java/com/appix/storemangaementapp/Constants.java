package com.appix.storemangaementapp;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Constants {
    Context context;


    public Constants(Context context){
        this.context = context;
    }

    public SharedPreferences getUser(){
        return context.getSharedPreferences("UserDetails", MODE_PRIVATE);
    }

    public boolean isLoggedIn(){
        SharedPreferences user = context.getSharedPreferences("UserDetails", MODE_PRIVATE);

//        String mobile= String.valueOf(user.contains("mobile_no"));
//        String usertype= String.valueOf(user.contains("user_type"));

            return user.contains("email");

    }
}
