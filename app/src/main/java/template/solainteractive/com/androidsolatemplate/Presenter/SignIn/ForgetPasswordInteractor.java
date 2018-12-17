package template.solainteractive.com.androidsolatemplate.Presenter.SignIn;

import retrofit2.Call;
import retrofit2.Response;
import template.solainteractive.com.androidsolatemplate.Contract.ForgetPasswordContract;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionCallbackPresenter;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionManagerPresenter;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;

public class ForgetPasswordInteractor implements ForgetPasswordContract.Interactor {

    private ForgetPasswordContract.Presenter forgetPasswordPresenter;
    private ConnectionManagerPresenter connectionManagerPresenter;

    public ForgetPasswordInteractor(ForgetPasswordContract.Presenter forgetPasswordPresenter){
        this.forgetPasswordPresenter = forgetPasswordPresenter;
    }

    @Override
    public void initRetrofitForgetPasswordAPI(Call call) {
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                MainResponse mainResponse = (MainResponse) response.body();

                forgetPasswordPresenter.onSuccessForgetPassword(mainResponse);
            }

            @Override
            public void onFailedResponse(Call call, Response response, String message) {
                forgetPasswordPresenter.onFailedForgetPassword(message);
            }

            @Override
            public void onFailure(Call call, String message) {

            }
        });
    }
}
