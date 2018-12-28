package template.solainteractive.com.androidsolatemplate.view.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;
import template.solainteractive.com.androidsolatemplate.BuildConfig;
import template.solainteractive.com.androidsolatemplate.Contract.SigninContract;
import template.solainteractive.com.androidsolatemplate.MyApplication;
import template.solainteractive.com.androidsolatemplate.Presenter.SignIn.SigninPresenter;
import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.Utils.Utils;
import template.solainteractive.com.androidsolatemplate.base.BaseActivity;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.view.ShowTerminalActivity;

public class SigninActivity extends BaseActivity implements SigninContract.View {


    private SigninContract.Presenter signInPresenter;
    @BindView(R.id.linearAppVersion)
    LinearLayout linearAppVersion;
    @BindView(R.id.rlSignIn)
    RelativeLayout rlSignIn;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.ivLogoRecharge)
    ImageView ivLogoRecharge;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.tvForgotPasswordClick)
    TextView tvForgotPasswordClick;
    @BindView(R.id.linearForgotPassword)
    LinearLayout linearForgotPassword;
    @BindView(R.id.tv_appVersion)
    TextView tvAppVersion;

    public String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String email, password;
    String notification_token = "abc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        signInPresenter = new SigninPresenter(this);
        tvAppVersion.setText(BuildConfig.VERSION_NAME);

        signInPresenter.onSignIn();
        clickHere();
    }

    public void clickHere() {
        tvForgotPasswordClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetPassword = new Intent(SigninActivity.this, ForgetPasswordActivity.class);
                startActivity(forgetPassword);
            }
        });
    }

    @Override
    public void setSignIn() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if (email.isEmpty()) {
                    Utils.showSnackBar(rlSignIn,"Email must be filled");
//                    Toast.makeText(SigninActivity.this, "Email must be filled!", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    Utils.showSnackBar(rlSignIn,"Email must be match with email pattern");
//                    Toast.makeText(SigninActivity.this, "Email must be match with email Pattern", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Utils.showSnackBar(rlSignIn,"Password must be filled");
//                    Toast.makeText(SigninActivity.this, "Password must be filled!", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 4 || password.length() > 8) {
                    Utils.showSnackBar(rlSignIn,"Password must be between 4 and 8");
//                    Toast.makeText(SigninActivity.this, "Password must be between 4 and 8", Toast.LENGTH_SHORT).show();
                } else {
                    signInPresenter.onPostLoginAPI(email, password, MyApplication.getInstance().getAccessToken());
                }
            }
        });
    }

    @Override
    public void setIntentToShowTerminalActivity(MainResponse mainResponse) {
        Intent login = new Intent(SigninActivity.this, ShowTerminalActivity.class);
        System.out.println("token_login : " + MyApplication.getInstance());
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        finish();
    }

    @Override
    public void setOnFailedPostLoginAPI(String message) {
        dismissProgressDialog();
        Utils.showSnackBar(rlSignIn, message);
//        Toast.makeText(SigninActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setOnFailurePostLoginAPI(String message) {
        dismissProgressDialog();
        Utils.showSnackBar(rlSignIn, message);
//        Toast.makeText(SigninActivity.this, "FAILURE", Toast.LENGTH_LONG).show();
    }
}
