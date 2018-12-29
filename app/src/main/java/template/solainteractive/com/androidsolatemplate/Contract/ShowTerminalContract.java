package template.solainteractive.com.androidsolatemplate.Contract;

import java.util.List;

import retrofit2.Call;
import template.solainteractive.com.androidsolatemplate.base.BaseView;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.model.Terminal;
import template.solainteractive.com.androidsolatemplate.model.TerminalModel;

public interface ShowTerminalContract {

    interface View extends BaseView {
        void setAdapter(TerminalModel terminalModel, List<Terminal> terminalList);
        void intentToSignInActivity(String message);
        void showSnackbar(String message);
        void setOnClick(TerminalModel terminalModel, List<Terminal> terminalList);
        void intentToSignInActivityforLogout();
        void getRefreshTerminalList();
        void showStatusMessage(MainResponse mainResponse);
        void searchMesaage(String message);
        void emptyData();
    }

    interface Presenter{
        void onGetTerminalAPI();
        void onPostLogoutAPI();
        void onDeleteTerminalAPI(String terminalId);

        void onClick(TerminalModel terminalModel, List<Terminal> terminalList);

        void onSuccessGetTerminal(TerminalModel terminalModel, List<Terminal> terminalList);
        void onFailedGetTerminal(String message);
        void onFailureGetTerminal(String message);
        void onSuccessPostLogout(MainResponse mainResponse);
        void onFailedPostLogout(String message);
        void onFailurePostLogout(String message);
        void onSuccessDeleteTerminal(MainResponse mainResponse);
        void onFailedDeleteTerminal(MainResponse mainResponse);
        void onSearchTerminal(String SEARCH_KEYWORDS);
        void onSuccessSearchTerminal(TerminalModel terminalModel, List<Terminal> terminalList);
        void onFailedSearchTerminal(String message);
        //SAMPAI SINI


    }

    interface Interactor{
        void initRetrofitGetTerminalCall(Call call);
        void initRetrofitPostLogoutAPI(Call call);
        void initRetrofitDeleteTerminalAPI(Call call);
        //INI BARU
        void initRetrofitSearchTerminalAPI(Call call);
    }
}
