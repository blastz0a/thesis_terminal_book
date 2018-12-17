package template.solainteractive.com.androidsolatemplate.Contract;


import retrofit2.Call;
import template.solainteractive.com.androidsolatemplate.base.BaseView;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;

public interface ForgetPasswordContract {

    interface View extends BaseView{
        void setOnSuccessForgetPassword();
        void setOnFailedForgetPassword(String message);
        void setUpView();
    }

    interface Presenter{
        void setForgetPassword(String etEmail);
        void onSetUpView();

        void onSuccessForgetPassword(MainResponse mainResponse);
        void onFailedForgetPassword(String message);
    }

    interface Interactor{
        void initRetrofitForgetPasswordAPI(Call call);
    }

}
