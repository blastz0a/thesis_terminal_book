package template.solainteractive.com.androidsolatemplate.Presenter.Terminal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import template.solainteractive.com.androidsolatemplate.Contract.FormTerminalContract;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionCallbackPresenter;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionManagerPresenter;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.model.RateModel;
import template.solainteractive.com.androidsolatemplate.model.Terminal;
import template.solainteractive.com.androidsolatemplate.model.TerminalModel;

public class FormTerminalInteractor implements FormTerminalContract.Interactor {

    private FormTerminalContract.Presenter formTerminalPresenter;
    private ConnectionManagerPresenter connectionManagerPresenter;

    public FormTerminalInteractor(FormTerminalContract.Presenter formTerminalPresenter){
        this.formTerminalPresenter = formTerminalPresenter;
    }

    @Override
    public void initRetrofitGetRateAPI(Call call) {
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                RateModel rateModel = (RateModel) response.body();

                formTerminalPresenter.onSuccessGetRateAPI(rateModel);
            }

            @Override
            public void onFailedResponse(Call call, Response response, String message) {
                formTerminalPresenter.onFailedGetRateAPI(message);
            }

            @Override
            public void onFailure(Call call, String message) {
                formTerminalPresenter.onFailureGetRateAPI(message);
            }
        });
    }

    @Override
    public void initRetrofitPostTerminalAPI(Call call) {
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                MainResponse mainResponse = (MainResponse) response.body();

                formTerminalPresenter.onSuccessPostTerminalAPI(mainResponse);
            }

            @Override
            public void onFailedResponse(Call call, Response response, String message) {
                formTerminalPresenter.onFailedPostTerminalAPI(message);
            }

            @Override
            public void onFailure(Call call, String message) {
                formTerminalPresenter.onFailurePostTerminalAPI(message);
            }
        });
    }

    @Override
    public void initRetrofitUpdateTerminalAPI(Call call) {
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                MainResponse mainResponse = (MainResponse) response.body();

                formTerminalPresenter.onSuccessUpdateTerminalAPI(mainResponse);
            }

            @Override
            public void onFailedResponse(Call call, Response response, String message) {
                formTerminalPresenter.onFailedUpdateTerminalAPI(message);
            }

            @Override
            public void onFailure(Call call, String message) {
                formTerminalPresenter.onFailureUpdateTerminalAPI(message);
            }
        });
    }
}
