package template.solainteractive.com.androidsolatemplate.view.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;
import template.solainteractive.com.androidsolatemplate.Contract.ForgetPasswordContract;
import template.solainteractive.com.androidsolatemplate.Presenter.SignIn.ForgetPasswordPresenter;
import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.Utils.Utils;
import template.solainteractive.com.androidsolatemplate.base.BaseActivity;
import template.solainteractive.com.androidsolatemplate.view_interface.SnackBarOnClick;

public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordContract.View {

    @BindView(R.id.tvToolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbarList)
    Toolbar toolbarList;
    private ForgetPasswordContract.Presenter forgetPasswordPresenter;
    @BindView(R.id.ll_forget_password)
    LinearLayout ll_forgetPassword;
    @BindView(R.id.etEmail)
    TextView emailTxt;
    @BindView(R.id.btnForgetPassword)
    Button btnForgetPassword;

    String email;
    public String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        forgetPasswordPresenter = new ForgetPasswordPresenter(this);
        forgetPasswordPresenter.onSetUpView();
        forgetPassword();
        back();
    }

    public void forgetPassword() {
        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailTxt.getText().toString();

                if (email.isEmpty()) {
                    Utils.showSnackBar(ll_forgetPassword,getString(R.string.input_email));
                } else if (!email.matches(emailPattern)) {
                    Utils.showSnackBar(ll_forgetPassword,getString(R.string.email_not_match));
                } else {
                    forgetPasswordPresenter.setForgetPassword(email);
                }
            }
        });
    }

    public void back() {
        toolbarList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setOnSuccessForgetPassword() {
        emailTxt.setText("");
        btnForgetPassword.setEnabled(false);
        Utils.showInfiniteSnackBar(ll_forgetPassword, getString(R.string.check_email), "OK", new SnackBarOnClick() {
            @Override
            public void onSnackBarClick() {
                onBackPressed();
            }
        });
    }

    @Override
    public void setOnFailedForgetPassword(String message) {
        Utils.showSnackBar(ll_forgetPassword, message);
    }

    @Override
    public void setUpView() {
        Utils.setupAppToolbarForActivity(ForgetPasswordActivity.this, toolbarList, getString(R.string.title_forget_password));
    }
}
