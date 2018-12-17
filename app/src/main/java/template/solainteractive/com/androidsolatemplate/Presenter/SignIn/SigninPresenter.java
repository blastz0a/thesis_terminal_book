package template.solainteractive.com.androidsolatemplate.Presenter.SignIn;

import retrofit2.Call;
import template.solainteractive.com.androidsolatemplate.Contract.SigninContract;
import template.solainteractive.com.androidsolatemplate.MyApplication;
import template.solainteractive.com.androidsolatemplate.connection.APIBody;
import template.solainteractive.com.androidsolatemplate.connection.RetrofitServices;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;

public class SigninPresenter implements SigninContract.Presenter{

    private SigninContract.View signInView;
    private SigninContract.Interactor signInInteractor;


    String email, password;

    public SigninPresenter(SigninContract.View signInView){
        this.signInView = signInView;
        this.signInInteractor = new SigninInteractor(this);
    }

    @Override
    public void onSignIn() {
        signInView.setSignIn();
    }

    @Override
    public void onPostLoginAPI(String email, String password, String notification_token) {
        signInView.showProgressDialog();
        Call call = RetrofitServices.retrofitLoginRequest().login(APIBody.loginBody(email, password, notification_token));
        signInInteractor.initRetrofitPostLoginAPI(call);

    }

    @Override
    public void onSuccessPostLoginAPI(MainResponse mainResponse) {
        MyApplication.getInstance().setLogin(email, mainResponse.getAccessToken(), password);
        signInView.setIntentToShowTerminalActivity(mainResponse);

    }

    @Override
    public void onFailedPostLoginAPI(String message) {
        signInView.setOnFailedPostLoginAPI(message);
    }

    @Override
    public void onFailurePostLoginAPI(String message) {
        signInView.setOnFailurePostLoginAPI(message);
    }
}
