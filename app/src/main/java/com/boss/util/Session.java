package com.boss.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.boss.view.activity.LoginActivity;


public class Session {

    private static final String UserData = "UserData";
    public static final String User_Id = "user_id";
    public static final String Name = "name";
    public static final String Mobile = "mobile";
    public static final String Email = "email";
    public static final String ProfilePic = "profilepic";
    private static final String IS_LOGGEDIN = "isLoggedIn";
    private final Context _context;


    private static SharedPreferences userData ;
    private final SharedPreferences.Editor editor ;

    public Session(Context context) {
        this._context = context;
        userData = _context.getSharedPreferences(UserData, Context.MODE_PRIVATE);
        editor = userData.edit();
        editor.apply();

    }

    public  void setUser_Id( String user_id) {
        SharedPreferences.Editor editor = userData.edit();
        editor.putString(User_Id, user_id);
        editor.apply();
    }
    public  void setValue(String key, String value) {
        SharedPreferences.Editor editor = userData.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getValue(String key) {
        return userData.getString(key, "");
    }

    public String getUser_Id() {
        return userData.getString(User_Id, "");
    }

    public  void setName( String Name) {
        SharedPreferences.Editor editor = userData.edit();
        editor.putString(Name, Name);
        editor.apply();
    }

    public  String getName() {

        return userData.getString(Name, "");
    }

    public  void setEmail( String Email) {
        SharedPreferences.Editor editor = userData.edit();
        editor.putString(Email, Email);
        editor.apply();
    }

    public  String getEmail() {

        return userData.getString(Email, "");
    }

    public void setProfilePic( String ProfilePic1) {
        SharedPreferences.Editor editor = userData.edit();
        editor.putString(ProfilePic, ProfilePic1);
        editor.apply();
    }

    public String getProfilePic() {
        return userData.getString(ProfilePic, "");
    }

    public void setLogin(boolean isLoggedIn) {
        SharedPreferences.Editor editor = userData.edit();
        editor.putBoolean(IS_LOGGEDIN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        //return mypref.getBoolean(IS_LOGGEDIN, false);
        return userData.getBoolean(IS_LOGGEDIN, false);
    }

    public void logout() {
        editor.clear();
        editor.apply();
        Intent showLogin = new Intent(_context, LoginActivity.class);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(showLogin);
    }

}
