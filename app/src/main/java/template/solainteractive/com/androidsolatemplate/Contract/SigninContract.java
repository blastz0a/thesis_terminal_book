package template.solainteractive.com.androidsolatemplate.Contract;

import retrofit2.Call;
import retrofit2.Response;
import template.solainteractive.com.androidsolatemplate.base.BaseView;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;

public interface SigninContract {

    interface View extends BaseView{
        void setSignIn();
        void setIntentToShowTerminalActivity(MainResponse mainResponse);
        void setOnFailedPostLoginAPI(String message);
        void setOnFailurePostLoginAPI(String message);
    }

    interface Presenter{
        void onSignIn();
        void onPostLoginAPI(String email, String password, String notification_token);

        void onFailedPostLoginAPI(String message);
        void onFailurePostLoginAPI(String message);

        void onSuccessPostLoginAPI(MainResponse mainResponse);
    }

    interface Interactor{
        void initRetrofitPostLoginAPI(Call call);
    }
}
