package template.solainteractive.com.androidsolatemplate.view.SignIn;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import template.solainteractive.com.androidsolatemplate.Contract.ChangePasswordContract;
import template.solainteractive.com.androidsolatemplate.Presenter.SignIn.ChangePasswordPresenter;
import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.Utils.Utils;
import template.solainteractive.com.androidsolatemplate.base.BaseActivity;
import template.solainteractive.com.androidsolatemplate.view_interface.SnackBarOnClick;

public class ChangePasswordActivity extends BaseActivity implements ChangePasswordContract.View {

    @BindView(R.id.toolbarList)
    Toolbar toolbarList;
    @BindView(R.id.tvToolbar)
    TextView tvToolbar;
    private ChangePasswordContract.Presenter changePasswordPresenter;
    @BindView(R.id.et_current_password)
    EditText etCurrentPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.ll_change_password)
    LinearLayout changePassword;
    @BindView(R.id.btn_change_password)
    Button btnChangePassword;
    String oldPasswordTxt, newPasswordTxt, confirmPasswordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        changePasswordPresenter = new ChangePasswordPresenter(this);

//        setupTypeFace();
        changePasswordPresenter.onSetupView();
        setSupportActionBar(toolbarList);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarList.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);


        etCurrentPassword.addTextChangedListener(textWatcher);
        etNewPassword.addTextChangedListener(textWatcher);
        etConfirmPassword.addTextChangedListener(textWatcher);
        btnChangePassword.setEnabled(false);

        back();
    }

    @OnClick(R.id.btn_change_password)
    public void onBtnClicked() {
//        changePassword();
        changePasswordPresenter.onChangePassword(oldPasswordTxt, newPasswordTxt);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
//            validationChangePassword();
            changePasswordPresenter.onValidateChangePassword();
        }
    };

//    public void setupTypeFace() {
//        etCurrentPassword.setTypeface(Typeface.DEFAULT);
//        etNewPassword.setTypeface(Typeface.DEFAULT);
//        etConfirmPassword.setTypeface(Typeface.DEFAULT);
//    }

//    public void validationChangePassword() {
//        oldPasswordTxt = etCurrentPassword.getText().toString();
//        newPasswordTxt = etNewPassword.getText().toString();
//        confirmPasswordTxt = etConfirmPassword.getText().toString();
//
//        if (oldPasswordTxt.length() != 0 && newPasswordTxt.length() != 0 && confirmPasswordTxt.length() != 0 && !oldPasswordTxt.equals(newPasswordTxt) && newPasswordTxt.equals(confirmPasswordTxt)) {
//            btnChangePassword.setEnabled(true);
//        } else {
//            btnChangePassword.setEnabled(false);
//        }
//    }

//    public void changePassword() {
//        Call call = RetrofitServices.retrofitRequest().changePassword(APIBody.changePassword(oldPasswordTxt, newPasswordTxt));
//        ConnectionManager connectionManager = new ConnectionManager(ChangePasswordActivity.this);
//        connectionManager.connect(call, new ConnectionCallback() {
//            @Override
//            public void onSuccessResponse(Call call, Response response) {
//                btnChangePassword.setEnabled(false);
//                Utils.showInfinteSnackBar(changePassword, getString(R.string.change_password_success), "OK", new SnackBarOnClick() {
//                    @Override
//                    public void onSnackBarClick() {
//                        onBackPressed();
//                    }
//                });
//            }
//
//            @Override
//            public void onFailedResponse(Call call, Response response, String message) {
//                Utils.showSnackBar(changePassword, message);
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//            }
//        });
//    }

    public void back() {
        toolbarList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setUpToolbar() {
        Utils.setupAppToolbarForActivity(ChangePasswordActivity.this, toolbarList, "Change Password");
    }

    @Override
    public void setOnSuccessChangePasswordAPI() {
        btnChangePassword.setEnabled(false);
        Utils.showInfiniteSnackBar(changePassword, getString(R.string.change_password_success), "OK", new SnackBarOnClick() {
            @Override
            public void onSnackBarClick() {
                onBackPressed();
            }
        });
    }

    @Override
    public void setOnFailedChangePasswordAPI() {
        Utils.showSnackBar(changePassword,"Failed Change Password");
    }

    @Override
    public void showSnackbar(String message) {
        Utils.showSnackBar(changePassword, message);
    }

    @Override
    public void setOnValidateChangePassword() {
        oldPasswordTxt = etCurrentPassword.getText().toString();
        newPasswordTxt = etNewPassword.getText().toString();
        confirmPasswordTxt = etConfirmPassword.getText().toString();

        if (oldPasswordTxt.length() != 0 && newPasswordTxt.length() != 0 && confirmPasswordTxt.length() != 0 && !oldPasswordTxt.equals(newPasswordTxt) && newPasswordTxt.equals(confirmPasswordTxt)) {
            btnChangePassword.setEnabled(true);
        } else {
            btnChangePassword.setEnabled(false);
        }
    }

    @Override
    public void setUpTypeFace() {
        etCurrentPassword.setTypeface(Typeface.DEFAULT);
        etNewPassword.setTypeface(Typeface.DEFAULT);
        etConfirmPassword.setTypeface(Typeface.DEFAULT);
    }
}
