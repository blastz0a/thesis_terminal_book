package template.solainteractive.com.androidsolatemplate;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.json.JSONObject;

import java.util.ArrayList;

import template.solainteractive.com.androidsolatemplate.view.SplashActivity;
import template.solainteractive.com.androidsolatemplate.view_interface.FirebaseRemoteConfigurationOnNext;

/**
 * Created by BillySaputra on 24-Aug-17.
 */

public class FirebaseRemoteConfiguration {
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private int minVer, currVer, deviceVerCode;
    private String abJsonObject;
    private boolean isMaintenance;
    private Activity mActivity;
    private FirebaseRemoteConfigurationOnNext firebaseRemoteConfigurationOnNext;
    private ArrayList<String> devicesArray = new ArrayList<>();
    private ArrayList<String> loginTypeArray = new ArrayList<>();

    public FirebaseRemoteConfiguration(Activity mActivity, FirebaseRemoteConfigurationOnNext firebaseRemoteConfigurationOnNext) {
        this.mActivity = mActivity;
        this.firebaseRemoteConfigurationOnNext = firebaseRemoteConfigurationOnNext;
    }
    public void setupFirebaseRemoteConfig(){
        try{
            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setDeveloperModeEnabled(BuildConfig.DEBUG)
                    .build();
            mFirebaseRemoteConfig.setConfigSettings(configSettings);
            mFirebaseRemoteConfig.setDefaults(R.layout.activity_splash);

            mFirebaseRemoteConfig.fetch(Constants.FETCH_FIREBASE)
                    .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                try{
                                    isMaintenance = mFirebaseRemoteConfig.getBoolean(Constants.FirebaseRemoteConfig.IS_MAINTENANCE);
                                    minVer = Integer.valueOf(mFirebaseRemoteConfig.getString(Constants.FirebaseRemoteConfig.MIN_VERSION));
                                    currVer = Integer.valueOf(mFirebaseRemoteConfig.getString(Constants.FirebaseRemoteConfig.CURRENT_VERSION));
                                    abJsonObject = mFirebaseRemoteConfig.getString(Constants.FirebaseRemoteConfig.TEST_AB);

                                    getABTestObject();

                                    validateFirebaseRemoteConfig();

                                    System.out.println("isABTesting: "+ abJsonObject);
                                    System.out.println("isMaintain: "+ minVer + " " + currVer + " "+isMaintenance);
                                    System.out.println("fetchFirebase : "+Constants.FETCH_FIREBASE);

                                }catch (NumberFormatException e){
                                    firebaseRemoteConfigurationOnNext.onFirebaseFetchFailed();
                                }
                            }
                        }
                    });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void getABTestObject(){
        boolean active = false;
        String allDevices = "";
        try{
            JSONObject object = new JSONObject(abJsonObject);
            active = object.getBoolean(Constants.ObjectJSON.ACTIVE);
            allDevices = object.getString(Constants.ObjectJSON.ALL_DEVICE);
            for(int i = 0; i < object.getJSONArray(Constants.ObjectJSON.DEVICES).length();i++){
                devicesArray.add((String) object.getJSONArray(Constants.ObjectJSON.DEVICES).get(i));
            }

            for (int i = 0; i < object.getJSONArray(Constants.ObjectJSON.LOGIN).length() ; i++){
                loginTypeArray.add((String) object.getJSONArray(Constants.ObjectJSON.LOGIN).get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        MyApplication.getInstance().setABActive(active);

        if (active) {
            MyApplication.getInstance().setABAllDevices(allDevices);
            MyApplication.getInstance().setDevicesArray(devicesArray);
            MyApplication.getInstance().setLoginArray(loginTypeArray);
        }
    }
    public void validateFirebaseRemoteConfig(){
        try{
            PackageInfo pInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            deviceVerCode = pInfo.versionCode;
            System.out.println("RC VALIDATION : "+deviceVerCode+ " "+ minVer + " " + currVer);
            if (isMaintenance) {
                firebaseRemoteConfigurationOnNext.onDismissProgressDialog();
                initMaintenanceDialog();
            } else if (deviceVerCode < minVer) {
                System.out.println("RC FORCE : "+deviceVerCode+" < "+minVer);
                firebaseRemoteConfigurationOnNext.onDismissProgressDialog();
                initForceUpdateDialog();
            } else if (deviceVerCode < currVer) {
                if (mActivity instanceof SplashActivity) {
                    System.out.println("RC SUGGEST : "+deviceVerCode+" < "+currVer);
                    initSuggestUpdateDialog();
                } else {
                    firebaseRemoteConfigurationOnNext.onNextAction();
                }
            } else {
                firebaseRemoteConfigurationOnNext.onNextAction();
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void initMaintenanceDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
        dialog.setTitle(mActivity.getString(R.string.maintenance_title));

        dialog.setPositiveButton(mActivity.getString(R.string.exit_button_title), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mActivity instanceof SplashActivity) {
                    mActivity.finish();
                } else {
                    mActivity.moveTaskToBack(true);
                }

                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = dialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        alert.show();

        Button posButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        posButton.setTextColor(ContextCompat.getColor(mActivity, R.color.color_red_A700));
    }
    private void initForceUpdateDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
        dialog.setTitle(mActivity.getString(R.string.force_update_title));

        dialog.setPositiveButton(mActivity.getString(R.string.update_button_title), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /**
                 * Function to force user to update from playstore
                 * */
                //goToPlayStore();
                dialogInterface.dismiss();
            }
        });

        dialog.setNegativeButton(mActivity.getString(R.string.exit_button_title), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mActivity instanceof SplashActivity) {
                    mActivity.finish();
                } else {
                    mActivity.moveTaskToBack(true);
                }

                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = dialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        alert.show();

        Button posButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        posButton.setTextColor(ContextCompat.getColor(mActivity, R.color.color_red_A700));
        negButton.setTextColor(ContextCompat.getColor(mActivity, R.color.color_red_A700));
    }
    private void initSuggestUpdateDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
        dialog.setTitle(mActivity.getString(R.string.suggest_update_title));

        dialog.setPositiveButton(mActivity.getString(R.string.update_button_title), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /**
                 * Function to force user to update from playstore
                 * */
                goToPlayStore();
                dialogInterface.dismiss();
            }
        });

        dialog.setNegativeButton(mActivity.getString(R.string.continue_button_title), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseRemoteConfigurationOnNext.onContinueOnClick();
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = dialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        alert.show();

        Button posButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        posButton.setTextColor(ContextCompat.getColor(mActivity, R.color.color_red_A700));
        negButton.setTextColor(ContextCompat.getColor(mActivity, R.color.color_red_A700));
    }
    private void goToPlayStore() {
        Toast.makeText(mActivity, "Going to Play Store", Toast.LENGTH_SHORT).show();
        /*Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + mActivity.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            mActivity.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mActivity, " unable to find market app", Toast.LENGTH_LONG).show();
        }*/
    }
}
