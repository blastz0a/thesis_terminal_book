package template.solainteractive.com.androidsolatemplate.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import template.solainteractive.com.androidsolatemplate.base.BaseView;
import template.solainteractive.com.androidsolatemplate.ProgressDialogConnection;

//
public class BaseActivity extends AppCompatActivity implements BaseView {
//
//
    ProgressDialogConnection progressDialogConnection;
//
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialogConnection = new ProgressDialogConnection();
    }
//
//    @Override
//    public void handleInvalidToken() {
//        Utils.intentWithClearTask(this,SigninActivity.class);
//        UserPereference.getInstance().set;
//    }
//
//    @Override
//    public void showMaintenanceDialog() {
//
//    }
//
//    @Override
//    public void showForceUpdateDialog() {
//
//    }
//
//    @Override
//    public void showServerErrorDialog() {
//
//    }
//
    @Override
    public void showProgressDialog() {
        progressDialogConnection.showProgressDialog(this, true);
    }

    @Override
    public void dismissProgressDialog() {
        progressDialogConnection.dismissProgressDialog();
    }
}
