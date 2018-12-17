package template.solainteractive.com.androidsolatemplate;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by BillySaputra on 22-Aug-17.
 */

public class SharedPreferenceCustom {
    public static final String PREFS_NAME = "SHARED_PREF";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static SharedPreferenceCustom instance = null;

    private SharedPreferenceCustom(Context context) {//constructor
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceCustom getInstance(Context context) {
        if(instance == null) //validate
            instance = new SharedPreferenceCustom(context);
        return instance;
    }

    public void putSharedPref(String key, String value) {
        this.sharedPreferences.edit().putString(key, value).commit();
    }

    public void putSharedPrefInt(String key, int value) {
        this.sharedPreferences.edit().putInt(key, value).commit();
    }


    public void putSharedPrefLong(String key, long value) {
        this.sharedPreferences.edit().putLong(key, value).commit();
    }


    public void putSharedPrefBoolean(String key, Boolean value) {
        this.sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public void putSharedPrefStringArray(String key, ArrayList<String> values) {
        for(int i  = 0; i < values.size(); i++){
            putSharedPref(key + "_" + Integer.toString(i), values.get(i));
        }
        putSharedPrefInt(key + "_size", values.size());
    }

    public String getSharedPref(String key) {
        return this.sharedPreferences.getString(key, "");
    }

    public void removeSharedPref(String key) {
        this.sharedPreferences.edit().remove(key).commit();
    }

    public int getSharedPrefInt(String key) {
        return this.sharedPreferences.getInt(key, 0);
    }

    public Long getSharedPrefLong(String key) {
        return this.sharedPreferences.getLong(key, 0);
    }

    public Boolean getSharedPrefBoolean(String key) {
        return this.sharedPreferences.getBoolean(key, false);
    }

    public Boolean getSharedPrefBoolean(String key, boolean defaultBoolean) {
        return this.sharedPreferences.getBoolean(key, defaultBoolean);
    }

    public ArrayList<String> getSharedPrefStringArray(String key) {
        ArrayList<String> values = new ArrayList<>();
        for(int i = 0; i < getSharedPrefInt(key + "_size"); i++){
            values.add(getSharedPref(key + "_" + Integer.toString(i)));
        }
        return values;
    }

    public void clearSharedPrefs(){
        this.sharedPreferences.edit().clear().commit();
    }

    public boolean containsKey(String key){
        return this.sharedPreferences.contains(key);
    }
}
