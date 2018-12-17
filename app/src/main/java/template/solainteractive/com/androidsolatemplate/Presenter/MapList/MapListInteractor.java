package template.solainteractive.com.androidsolatemplate.Presenter.MapList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import template.solainteractive.com.androidsolatemplate.Contract.MapListContract;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionCallbackPresenter;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionManagerPresenter;
import template.solainteractive.com.androidsolatemplate.model.Terminal;
import template.solainteractive.com.androidsolatemplate.model.TerminalModel;

public class MapListInteractor implements MapListContract.Interactor{

    private MapListContract.Presenter mapListPresenter;
    private ConnectionManagerPresenter connectionManagerPresenter;
    private List<Terminal> terminalList;

    public MapListInteractor(MapListContract.Presenter mapListPresenter){
        this.mapListPresenter = mapListPresenter;
        connectionManagerPresenter = new ConnectionManagerPresenter();
    }

    @Override
    public void initRetrofitGetTerminalCall(Call call) {
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                TerminalModel terminalModel = (TerminalModel) response.body();
                terminalList = terminalModel.getTerminalList();
                System.out.println("Terminal List Size : " + terminalList.size());

                mapListPresenter.onSuccessGetTerminalAPI(terminalModel, terminalList);
            }

            @Override
            public void onFailedResponse(Call call, Response response, String message) {

            }

            @Override
            public void onFailure(Call call, String message) {

            }
        });
    }
}
