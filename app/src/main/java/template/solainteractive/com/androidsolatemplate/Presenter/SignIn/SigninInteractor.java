package template.solainteractive.com.androidsolatemplate.Presenter.SignIn;

import retrofit2.Call;
import retrofit2.Response;
import template.solainteractive.com.androidsolatemplate.Constants;
import template.solainteractive.com.androidsolatemplate.Contract.SigninContract;
import template.solainteractive.com.androidsolatemplate.MyApplication;

import template.solainteractive.com.androidsolatemplate.connection.ConnectionCallbackPresenter;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionManagerPresenter;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;

public class SigninInteractor implements SigninContract.Interactor{

    private SigninContract.Presenter signInPresenter;
    private ConnectionManagerPresenter connectionManagerPresenter;

    public SigninInteractor(SigninContract.Presenter signInPresenter){
        this.signInPresenter = signInPresenter;
    }

    @Override
    public void initRetrofitPostLoginAPI(Call call) {
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                MainResponse mainResponse = (MainResponse) response.body();

                signInPresenter.onSuccessPostLoginAPI(mainResponse);

            }

            @Override
            public void onFailedResponse(Call call, Response response, String message) {
                signInPresenter.onFailedPostLoginAPI(message);
            }

            @Override
            public void onFailure(Call call, String message) {
                signInPresenter.onFailurePostLoginAPI(message);
            }
        });
    }
}
