package template.solainteractive.com.androidsolatemplate.connection;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import template.solainteractive.com.androidsolatemplate.Constants;
import template.solainteractive.com.androidsolatemplate.MyApplication;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.ProgressDialogConnection;
import template.solainteractive.com.androidsolatemplate.Utils.Utils;
import template.solainteractive.com.androidsolatemplate.view.LoginActivity;

/**
 * Created by BillySaputra on 23-Aug-17.
 */

public class ConnectionManager <T extends Object>extends APIConnectionHandler{
    String TAG = "API_RESPONSE";
    Activity activity;
    ConnectionCallback mConnectionCallback;
    private ProgressDialogConnection progressDialogConnection = new ProgressDialogConnection();

    public ConnectionManager(Activity activity) {
        this.activity = activity;
    }

    public void connect(Call mCall, final ConnectionCallback mConnectionCallback) {
        progressDialogConnection.showProgressDialog(activity, isShowProgressDialog());
        this.mConnectionCallback = mConnectionCallback;
        mCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    progressDialogConnection.dismissProgressDialog();
                    MainResponse mainResponse = (MainResponse) response.body();

                    String status = mainResponse.getStatus();
                    String message = mainResponse.getMessage();
                    String URL =  response.raw().request().url().toString();

                    System.out.println("status: " + status + " " + message);
                    if(status.equals(Constants.Result.SUCCESS)) {
                        Log.i(TAG, "SUCCESS " + MyApplication.getInstance().getAccessToken());

                        if(mainResponse.getAccessToken() != null) {
                            System.out.println("url "+URL);
                            MyApplication.getInstance().setAccessToken(mainResponse.getAccessToken());
                            Log.i(TAG, "REFRESH TOKEN " + MyApplication.getInstance().getAccessToken());
                        }

                        mConnectionCallback.onSuccessResponse(call, response);

                    } else if(status.equals(Constants.Result.FAILED)) {
                        progressDialogConnection.dismissProgressDialog();

                        Log.i(TAG, "FAILED " +message);
                        if (message.equals(Constants.Result.INVALID_TOKEN)) {
                            Utils.intentWithClearTask((AppCompatActivity) activity, LoginActivity.class);
                            MyApplication.getInstance().setLogout();
                            MyApplication.getInstance().setLoginStatus();
                        }
                        mConnectionCallback.onFailedResponse(call, response, message);
                    }
                }
            }

            @Override
            public void onFailure(final Call call, Throwable t) {
                System.out.println("failure: " + t.toString());
                progressDialogConnection.dismissProgressDialog();

                /*if (!call.isCanceled()){
                    setErrorRetryCallBack(call);
                }*/
                Toast.makeText(activity, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                mConnectionCallback.onFailure(t.toString());
            }
        });
    }

    /*private void setErrorRetryCallBack(final Call<T> callAPI){
        Utils.showErrorDialog(activity, "", isShowErrorDialog(),new Utils.RetryButtonListener() {
            @Override
            public void onRetryButton() {
                retryCallApi(callAPI);
            }
        });
    }
    private void retryCallApi(Call<T> callAPI){
        try{
            connect(callAPI.clone(), mConnectionCallback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
