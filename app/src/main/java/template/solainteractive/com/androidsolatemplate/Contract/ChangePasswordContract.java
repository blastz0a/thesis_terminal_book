package template.solainteractive.com.androidsolatemplate.Contract;

import retrofit2.Call;
import template.solainteractive.com.androidsolatemplate.base.BaseView;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;

public interface ChangePasswordContract {

    interface View extends BaseView {
        void setUpToolbar();
        void setOnSuccessChangePasswordAPI();
        void setOnFailedChangePasswordAPI();
        void setOnValidateChangePassword();

        void setUpTypeFace();
        void showSnackbar(String message);

    }

    interface Presenter {

        void onChangePassword(String oldPassword, String newPassword);
        void onValidateChangePassword();
        void onSetupView();

        void onSuccessChangePasswordAPI(MainResponse mainResponse);
        void onFailedChangePasswordAPI(String message);

    }

    interface Interactor {

        void initRetrofitChangePasswordAPI(Call call);
    }

}
