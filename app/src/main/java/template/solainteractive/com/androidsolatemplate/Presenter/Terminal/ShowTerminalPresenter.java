package template.solainteractive.com.androidsolatemplate.Presenter.Terminal;

import java.util.List;

import retrofit2.Call;
import template.solainteractive.com.androidsolatemplate.Contract.ShowTerminalContract;
import template.solainteractive.com.androidsolatemplate.MyApplication;
import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.Utils.Utils;
import template.solainteractive.com.androidsolatemplate.connection.APIBody;
import template.solainteractive.com.androidsolatemplate.connection.RetrofitServices;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.model.Terminal;
import template.solainteractive.com.androidsolatemplate.model.TerminalModel;

public class ShowTerminalPresenter implements ShowTerminalContract.Presenter{

    private ShowTerminalContract.View showTerminalView;
    private ShowTerminalContract.Interactor showTerminalInteractor;

    public ShowTerminalPresenter(ShowTerminalContract.View showTerminalView) {
        this.showTerminalView = showTerminalView;
        this.showTerminalInteractor = new ShowTerminalInteractor(this);
    }

    String email, password;

    @Override
    public void onGetTerminalAPI() {
        showTerminalView.showProgressDialog();
        Call call = RetrofitServices.retrofitRequest().getTerminal();
        showTerminalInteractor.initRetrofitGetTerminalCall(call);

    }

    @Override
    public void onPostLogoutAPI() {
        Call call = RetrofitServices.retrofitRequest().logout(APIBody.logoutBody(email, password));
        showTerminalInteractor.initRetrofitPostLogoutAPI(call);
    }

    @Override
    public void onClick(TerminalModel terminalModel, List<Terminal> terminalList) {
        showTerminalView.dismissProgressDialog();
        showTerminalView.setOnClick(terminalModel, terminalList);
    }

    @Override
    public void onSuccessGetTerminal(TerminalModel terminalModel, List<Terminal> terminalList) {
        if(terminalList.size() > 0){
            System.out.println("Check Terminal Data : " + terminalList.get(0).getTerminalName());
        } else {
            System.out.println("size : " + terminalList.size());
        }
        showTerminalView.setAdapter(terminalModel, terminalList);
        showTerminalView.dismissProgressDialog();
    }

    @Override
    public void onFailedGetTerminal(String message) {
        showTerminalView.dismissProgressDialog();

        // need improvement, create snackbar

        // after improvemet
        showTerminalView.showSnackbar(message);

        /*before improvement
        showTerminalView.intentToSignInActivity(message);*/
    }

    @Override
    public void onFailureGetTerminal(String message) {
        showTerminalView.dismissProgressDialog();
        showTerminalView.showSnackbar(message);
    }

    @Override
    public void onSuccessPostLogout(MainResponse mainResponse) {
        MyApplication.getInstance().setLogout();
        showTerminalView.intentToSignInActivityforLogout();
    }

    @Override
    public void onFailedPostLogout(String message) {

    }

    @Override
    public void onFailurePostLogout(String message) {
        showTerminalView.showSnackbar(message);
    }

    @Override
    public void onDeleteTerminalAPI(String terminalId) {
        showTerminalView.showProgressDialog();
        Call call = RetrofitServices.retrofitRequest().deleteTerminal(APIBody.deleteTerminalBody(terminalId));
        showTerminalInteractor.initRetrofitDeleteTerminalAPI(call);
    }

    @Override
    public void onSuccessDeleteTerminal(MainResponse mainResponse) {
        showTerminalView.dismissProgressDialog();
        showTerminalView.getRefreshTerminalList();
    }

    @Override
    public void onFailedDeleteTerminal(MainResponse mainResponse) {
        showTerminalView.dismissProgressDialog();
        showTerminalView.showStatusMessage(mainResponse);
    }
    @Override
    public void onSearchTerminal(String SEARCH_KEYWORDS) {
        showTerminalView.showProgressDialog();
        Call call = RetrofitServices.retrofitRequest().searchTerminal(APIBody.searchTerminal(SEARCH_KEYWORDS));
        showTerminalInteractor.initRetrofitSearchTerminalAPI(call);
    }

    @Override
    public void onSuccessSearchTerminal(TerminalModel terminalModel, List<Terminal> terminalList) {
        if (terminalList.size() !=0 ){
            showTerminalView.setAdapter(terminalModel, terminalList);
            showTerminalView.dismissProgressDialog();
        }
        else if (terminalList.size() == 0){
            showTerminalView.dismissProgressDialog();
            showTerminalView.emptyData();
            showTerminalView.searchMesaage(String.valueOf(R.string.keywords_not_match));
        }
    }

    @Override
    public void onFailedSearchTerminal(String message) {
        showTerminalView.showSnackbar(message);
    }
}
