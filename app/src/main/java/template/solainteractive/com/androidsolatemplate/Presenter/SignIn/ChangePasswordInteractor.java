package template.solainteractive.com.androidsolatemplate.Presenter.SignIn;

import retrofit2.Call;
import retrofit2.Response;
import template.solainteractive.com.androidsolatemplate.Contract.ChangePasswordContract;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionCallbackPresenter;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionManagerPresenter;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;

public class ChangePasswordInteractor implements ChangePasswordContract.Interactor {

    private ChangePasswordContract.Presenter changePasswordPresenter;
    private ConnectionManagerPresenter connectionManagerPresenter;

    public ChangePasswordInteractor(ChangePasswordContract.Presenter changePasswordPresenter){
        this.changePasswordPresenter = changePasswordPresenter;
    }

    @Override
    public void initRetrofitChangePasswordAPI(Call call) {
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                MainResponse mainResponse = (MainResponse) response.body();

                changePasswordPresenter.onSuccessChangePasswordAPI(mainResponse);
            }

            @Override
            public void onFailedResponse(Call call, Response response, String message) {
                changePasswordPresenter.onFailedChangePasswordAPI(message);
            }

            @Override
            public void onFailure(Call call, String message) {

            }
        });
    }
}
