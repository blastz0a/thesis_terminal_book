package template.solainteractive.com.androidsolatemplate.Presenter.SignIn;

import android.content.Intent;

import retrofit2.Call;
import template.solainteractive.com.androidsolatemplate.Contract.ForgetPasswordContract;
import template.solainteractive.com.androidsolatemplate.connection.APIBody;
import template.solainteractive.com.androidsolatemplate.connection.RetrofitServices;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.view.SignIn.ForgetPasswordActivity;
import template.solainteractive.com.androidsolatemplate.view.SignIn.SigninActivity;

public class ForgetPasswordPresenter implements ForgetPasswordContract.Presenter {

    private ForgetPasswordContract.View forgetPasswordView;
    private ForgetPasswordContract.Interactor forgetPasswordInteractor;

    public ForgetPasswordPresenter(ForgetPasswordContract.View forgetPasswordView) {
        this.forgetPasswordView = forgetPasswordView;
        this.forgetPasswordInteractor = new ForgetPasswordInteractor(this);
    }


    @Override
    public void setForgetPassword(String etEmail) {
        Call call = RetrofitServices.retrofitRequest().forgetPassword(APIBody.forgetPassword(etEmail));
        forgetPasswordInteractor.initRetrofitForgetPasswordAPI(call);

    }

    @Override
    public void onSetUpView() {
        forgetPasswordView.setUpView();
    }

    @Override
    public void onSuccessForgetPassword(MainResponse mainResponse) {
        forgetPasswordView.setOnSuccessForgetPassword();
    }

    @Override
    public void onFailedForgetPassword(String message) {
        forgetPasswordView.setOnFailedForgetPassword(message);
    }
}
