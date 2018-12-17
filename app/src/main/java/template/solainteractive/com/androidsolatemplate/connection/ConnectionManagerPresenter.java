package template.solainteractive.com.androidsolatemplate.connection;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import template.solainteractive.com.androidsolatemplate.BuildConfig;
import template.solainteractive.com.androidsolatemplate.Constants;
import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;

public class ConnectionManagerPresenter {

    private String TAG = "API_RESPONSE";
    private ConnectionCallbackPresenter mConnectionCallback;
    private Call mCall;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private boolean isMaintenance;
    private int minVer, currVer;

    public void connect( Call mCall,  ConnectionCallbackPresenter mConnectionCallback) {
        this.mCall = mCall;
        this.mConnectionCallback = mConnectionCallback;
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        setupFirebaseRemoteConfig();
    }

    public void setupFirebaseRemoteConfig() {
        if (mFirebaseRemoteConfig != null) {
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setDeveloperModeEnabled(BuildConfig.DEBUG)
                    .build();
            mFirebaseRemoteConfig.setConfigSettings(configSettings);
            mFirebaseRemoteConfig.setDefaults(R.layout.activity_splash);

            mFirebaseRemoteConfig.fetch(Constants.FETCH_FIREBASE)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mFirebaseRemoteConfig.activateFetched();

                                try {
                                    isMaintenance = mFirebaseRemoteConfig.getBoolean(Constants.FirebaseRemoteConfig.IS_MAINTENANCE);
                                    minVer = Integer.valueOf(mFirebaseRemoteConfig.getString(Constants.FirebaseRemoteConfig.MIN_VERSION));
                                    currVer = Integer.valueOf(mFirebaseRemoteConfig.getString(Constants.FirebaseRemoteConfig.CURRENT_VERSION));

                                    validationFirebaseRemoteConfig();

                                } catch (NumberFormatException e) {
                                    mConnectionCallback.onFailedResponse(mCall, null, "SERVER_ERROR");
                                }
                            } else{
                                if(BuildConfig.DEBUG) {
                                    callAPIRequest();
                                } else {
                                    mConnectionCallback.onFailure(mCall, "");
                                }
                            }
                        }
                    });
        } else {
            System.out.println("failure firebase null");
            mConnectionCallback.onFailure(mCall, "");
        }
    }

    private void validationFirebaseRemoteConfig() {
        int deviceVerCode = BuildConfig.VERSION_CODE;
        System.out.println("RC VALIDATION : "+ deviceVerCode + " "+ minVer + " " + currVer);

        if (BuildConfig.FLAVOR.equalsIgnoreCase("AdminLive")) {
            callAPIRequest();
        } else {

            if (isMaintenance) {
                mConnectionCallback.onFailedResponse(mCall, null, "MAINTENANCE");
            } else if (deviceVerCode < minVer) {
                mConnectionCallback.onFailedResponse(mCall, null, "FORCE_UPDATE");
            }
//            else if (deviceVerCode < currVer && UserPreference.getInstance().getDontSkipSuggestUpdate()) {
//                mConnectionCallback.onFailedResponse(mCall, null, "SUGGEST_UPDATE");
//            }
            else {
                callAPIRequest();
            }
        }
    }

    public void callAPIRequest() {

        mCall.clone().enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i(TAG, "Response code : "+ response.code());
                Log.i(TAG, "Response url  : "+ response.raw().request().url().toString());

                if (response.isSuccessful()) { //ONLY FOR RESPONSE CODE 200
                    System.out.println("Response success");
                    MainResponse mainResponse = (MainResponse) response.body();
                    String status, message;

                    status = mainResponse.getStatus();
                    message = mainResponse.getMessage();

                    if(status.equals(Constants.Result.SUCCESS)) {
//                        Log.i(TAG, "SUCCESS : " + UserPreference.getInstance().getAccessToken());

                        if(mainResponse.getAccessToken() != null) {
//                            UserPreference.getInstance().setAccessToken(mainResponse.getAccessToken());
//                            Log.i(TAG, "REFRESH TOKEN " + UserPreference.getInstance().getAccessToken());
                        }

                        mConnectionCallback.onSuccessResponse(call, response);

                    } else if(status.equals(Constants.Result.FAILED)) {
                        Log.i(TAG, "FAILED : " +message);

                        mConnectionCallback.onFailedResponse(call, response, message);
                    }

                } else { // OTHER RESPONSE CODE SUCH AS 400 , 500
                    System.out.println("FAILURE");
                    mConnectionCallback.onFailedResponse(call, response, "SERVER_ERROR"); // dont put into string.xml since need activity to get the resource
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mConnectionCallback.onFailure(call, t.getMessage());
            }
        });
    }
}
