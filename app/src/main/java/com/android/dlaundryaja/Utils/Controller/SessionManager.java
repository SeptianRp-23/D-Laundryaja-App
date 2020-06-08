package com.android.dlaundryaja.Utils.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    //user
    private static final String PREF_NAME = "LOGIN";
    public static final String LOGIN = "IS_LOGIN";
    public static final String LOGIN_ADMIN = "IS_LOGED_IN";
    public static final String LOGIN_KURIR = "IS_LOGED_ON";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String LEVEL = "LEVEL";
    public static final String ID = "ID";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession (String name, String email, String id, String level){
        editor.putBoolean(LOGIN, true);
        editor.putBoolean(LOGIN_ADMIN, true);
        editor.putBoolean(LOGIN_KURIR, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(LEVEL, level);
        editor.putString(ID, id);
        editor.apply();
    }

//    public boolean isLogin(){
//        return sharedPreferences.getBoolean(LOGIN, false);
//    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public boolean isLogin2(){
        return sharedPreferences.getBoolean(LOGIN_ADMIN, false);
    }

    public boolean isLogin3(){
        return sharedPreferences.getBoolean(LOGIN_KURIR, false);
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(ID, sharedPreferences.getString(ID, null));

        return user;
    }
//Logout
//    public void logout(){
//        editor.clear();
//        editor.commit();
//        Intent intent = new Intent(context, LoginActivity.class);
//        context.startActivity(intent);
//        ((Read_Profile)context).finish();
//    }
}
