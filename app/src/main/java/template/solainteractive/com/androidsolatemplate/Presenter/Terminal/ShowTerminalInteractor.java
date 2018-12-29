package template.solainteractive.com.androidsolatemplate.Presenter.Terminal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import template.solainteractive.com.androidsolatemplate.Contract.ShowTerminalContract;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionCallbackPresenter;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionManagerPresenter;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.model.Terminal;
import template.solainteractive.com.androidsolatemplate.model.TerminalModel;

public class ShowTerminalInteractor implements ShowTerminalContract.Interactor{

    private ShowTerminalContract.Presenter showTerminalPresenter;
    private ConnectionManagerPresenter connectionManagerPresenter;

    public ShowTerminalInteractor(ShowTerminalContract.Presenter showTerminalPresenter){
        this.showTerminalPresenter = showTerminalPresenter;
    }

    @Override
    public void initRetrofitGetTerminalCall(Call call) {
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                TerminalModel terminalModel = (TerminalModel) response.body();
                showTerminalPresenter.onSuccessGetTerminal(terminalModel, terminalModel.getTerminalList());
            }

            @Override
            public void onFailedResponse(Call call, Response response, String message) {
                showTerminalPresenter.onFailedGetTerminal(message);
            }

            @Override
            public void onFailure(Call call, String message) {
                showTerminalPresenter.onFailureGetTerminal(message);
            }
        });
    }

    @Override
    public void initRetrofitPostLogoutAPI(Call call) {
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                MainResponse mainResponse = (MainResponse) response.body();
                showTerminalPresenter.onSuccessPostLogout(mainResponse);
            }

            @Override
            public void onFailedResponse(Call call, Response response, String message) {
                showTerminalPresenter.onFailedPostLogout(message);
            }

            @Override
            public void onFailure(Call call, String message) {
                showTerminalPresenter.onFailurePostLogout(message);
            }
        });
    }

    @Override
    public void initRetrofitDeleteTerminalAPI(Call call) {
        connectionManagerPresenter  = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
//                TerminalModel terminalModel = (TerminalModel) response.body();

                MainResponse mainResponse = (MainResponse) response.body();

                showTerminalPresenter.onSuccessDeleteTerminal(mainResponse);
            }

            @Override
            public void onFailedResponse(Call call, Response response, String message) {

            }

            @Override
            public void onFailure(Call call, String message) {

            }
        });
    }

    //INI FUNCTION BARU
    @Override
    public void initRetrofitSearchTerminalAPI(Call call) {
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                TerminalModel terminalModel = (TerminalModel) response.body();
                showTerminalPresenter.onSuccessSearchTerminal(terminalModel, terminalModel.getTerminalList());
            }

            @Override
            public void onFailedResponse(Call call, Response response, String message) {
                showTerminalPresenter.onFailedSearchTerminal(message);
            }

            @Override
            public void onFailure(Call call, String message) {

            }
        });
    }
}
