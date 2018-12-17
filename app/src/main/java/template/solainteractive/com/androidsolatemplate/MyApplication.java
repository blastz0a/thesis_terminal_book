package template.solainteractive.com.androidsolatemplate;

import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;


/**
 * Created by BillySaputra on 22-Aug-17.
 */

public class MyApplication extends Application{
    private static MyApplication mInstance;
    private SharedPreferenceCustom sharedPreferenceCustom;
    public Activity activity;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;
        sharedPreferenceCustom = SharedPreferenceCustom.getInstance(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    /** CHECK USER CURRENT STATUS **/
    public void setLoginStatus() {
        if (getEmail().isEmpty() && getAccessToken().isEmpty() && getPassword().isEmpty()) {
            sharedPreferenceCustom.putSharedPref(Constants.SharedPreference.LOGIN_STATUS, Constants.LogInStatus.NOT_LOGIN);
        } else {
            sharedPreferenceCustom.putSharedPref(Constants.SharedPreference.LOGIN_STATUS, Constants.LogInStatus.LOGIN);
        }
    }

    public String getAccessToken() {
        return sharedPreferenceCustom.getSharedPref(Constants.SharedPreference.ACCESS_TOKEN);
    }
    public void setAccessToken(String accessToken) {
        sharedPreferenceCustom.putSharedPref(Constants.SharedPreference.ACCESS_TOKEN, accessToken);
    }


    public String getEmail() {
        return sharedPreferenceCustom.getSharedPref(Constants.SharedPreference.EMAIL);
    }
    public String setEmail(String email) {
        sharedPreferenceCustom.putSharedPref(Constants.SharedPreference.EMAIL, email);
        return email;
    }
    public  String getPassword(){
        return sharedPreferenceCustom.getSharedPref(Constants.SharedPreference.PASSWORD);
    }
    public String setPassword(String password){
        sharedPreferenceCustom.putSharedPref(Constants.SharedPreference.PASSWORD, password);
        return password;
    }

    public String getName(){
        return sharedPreferenceCustom.getSharedPref(Constants.SharedPreference.NAME);
    }
    public void setName(String name) {
        sharedPreferenceCustom.putSharedPref(Constants.SharedPreference.NAME, name);
    }

    public boolean getABActive() {
        return sharedPreferenceCustom.getSharedPrefBoolean(Constants.SharedPreference.AB_ACTIVE);
    }
    public void setABActive(boolean active) {
        sharedPreferenceCustom.putSharedPrefBoolean(Constants.SharedPreference.AB_ACTIVE, active);
    }

    public ArrayList<String> getDevicesArray(){
        return sharedPreferenceCustom.getSharedPrefStringArray(Constants.SharedPreference.DEVICES_ARRAY);
    }
    public void setDevicesArray(ArrayList<String> devicesArray) {
        sharedPreferenceCustom.putSharedPrefStringArray(Constants.SharedPreference.DEVICES_ARRAY, devicesArray);
    }

    public ArrayList<String> getLoginArray(){
        return sharedPreferenceCustom.getSharedPrefStringArray(Constants.SharedPreference.LOGIN_ARRAY);
    }
    public void setLoginArray(ArrayList<String> loginArray) {
        sharedPreferenceCustom.putSharedPrefStringArray(Constants.SharedPreference.LOGIN_ARRAY, loginArray);
    }

    public String getABAllDevices() {
        return sharedPreferenceCustom.getSharedPref(Constants.SharedPreference.AB_ALL_DEVICES);
    }
    public void setABAllDevices(String allDevices) {
        sharedPreferenceCustom.putSharedPref(Constants.SharedPreference.AB_ALL_DEVICES, allDevices);
    }

    public void setLogin(String email, String accessToken, String password) {
        setEmail(email);
        setPassword(password);
        setAccessToken(accessToken);
        setLoginStatus();
    }
    public String getLoginStatus() {
        return sharedPreferenceCustom.getSharedPref(Constants.SharedPreference.LOGIN_STATUS);
    }

    public void setLogout(){
        setEmail(null);
        setName(null);
        setPassword(null);
        setAccessToken(null);
        setLoginStatus();
    }

}
