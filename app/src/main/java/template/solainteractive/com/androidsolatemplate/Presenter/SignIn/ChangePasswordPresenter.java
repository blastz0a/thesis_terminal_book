package template.solainteractive.com.androidsolatemplate.Presenter.SignIn;

import retrofit2.Call;
import template.solainteractive.com.androidsolatemplate.Contract.ChangePasswordContract;
import template.solainteractive.com.androidsolatemplate.connection.APIBody;
import template.solainteractive.com.androidsolatemplate.connection.RetrofitServices;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {

    private ChangePasswordContract.View changePasswordView;
    private ChangePasswordContract.Interactor changePasswordInteractor;


    public ChangePasswordPresenter(ChangePasswordContract.View changePasswordView){
        this.changePasswordView = changePasswordView;
        this.changePasswordInteractor = new ChangePasswordInteractor(this);
    }

    @Override
    public void onChangePassword(String oldPasswordTxt, String newPasswordTxt) {
        Call call = RetrofitServices.retrofitRequest().changePassword(APIBody.changePassword(oldPasswordTxt, newPasswordTxt));
        changePasswordInteractor.initRetrofitChangePasswordAPI(call);
    }

    @Override
    public void onValidateChangePassword() {
        changePasswordView.setOnValidateChangePassword();
    }

    @Override
    public void onSetupView() {
        changePasswordView.setUpTypeFace();
        changePasswordView.setUpToolbar();
    }

    @Override
    public void onSuccessChangePasswordAPI(MainResponse mainResponse) {
        changePasswordView.setOnSuccessChangePasswordAPI();
    }

    @Override
    public void onFailedChangePasswordAPI(String message) {
        changePasswordView.setOnFailedChangePasswordAPI();
    }

}
