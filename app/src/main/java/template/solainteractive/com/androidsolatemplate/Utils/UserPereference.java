package template.solainteractive.com.androidsolatemplate.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;

import template.solainteractive.com.androidsolatemplate.MyApplication;


public class UserPereference {
    public static final String PREFS_NAME = "SHARED_PREF";
    private SharedPreferences sharedPreferences;
    private static UserPereference userPereference;

    private UserPereference(Context context){
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private static UserPereference getInstance(Context context){
        if (userPereference == null){
            userPereference = new UserPereference(context.getApplicationContext());
        }
        return userPereference;
    }

    public static UserPereference getInstance(){
        if(userPereference != null){
            return userPereference;
        } else{
            return getInstance(MyApplication.getInstance());
        }
    }


}
