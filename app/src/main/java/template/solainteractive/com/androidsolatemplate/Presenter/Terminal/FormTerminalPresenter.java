package template.solainteractive.com.androidsolatemplate.Presenter.Terminal;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import template.solainteractive.com.androidsolatemplate.Contract.FormTerminalContract;
import template.solainteractive.com.androidsolatemplate.connection.APIBody;
import template.solainteractive.com.androidsolatemplate.connection.RetrofitServices;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.model.RateModel;

public class FormTerminalPresenter implements FormTerminalContract.Presenter {


    private FormTerminalContract.View formTerminalView;
    private FormTerminalContract.Interactor formTerminalInteractor;

    public FormTerminalPresenter(FormTerminalContract.View formTerminalView){
        this.formTerminalView = formTerminalView;
        this.formTerminalInteractor = new FormTerminalInteractor(this);
    }

    @Override
    public void showLocation() {
        formTerminalView.setShowLocation();
    }

    @Override
    public void loadAvatarImage() {
        formTerminalView.setLoadAvatarImage();
    }

    @Override
    public void btnScanClick() {
        formTerminalView.setBtnScanClick();
    }

    @Override
    public void btnMapClick() {
        formTerminalView.setBtnMapClick();
    }

    @Override
    public void getRateAPI() {
        Call call = RetrofitServices.retrofitRequest().getRate();
        formTerminalInteractor.initRetrofitGetRateAPI(call);
    }

    @Override
    public void onSuccessGetRateAPI(RateModel rateModel) {
        formTerminalView.setOnSuccessGetRateAPI(rateModel);
    }

    @Override
    public void onFailedGetRateAPI(String message) {
        formTerminalView.setOnFailedGetRateAPI(message);
    }

    @Override
    public void onFailureGetRateAPI(String message) {
        formTerminalView.setOnFailureGetRateAPI(message);
    }


    @Override
    public void postUploadTerminalAPI(String scanResult, double currLatitude, double currLongitude, String name, String address, String metatag, int typeID, String timeOpen, String timeClose, String network, String postalCode, String description, String activeStatus, int choosenOptionID, String encodedImage) {
        Call call = RetrofitServices.retrofitRequest().upload(APIBody.uploadBody(scanResult, currLatitude, currLongitude, name, address, metatag, typeID,
                timeOpen, timeClose, network, postalCode, description, activeStatus, choosenOptionID, encodedImage));
        formTerminalInteractor.initRetrofitPostTerminalAPI(call);
    }

    @Override
    public void postUpdateTerminalAPI(String scanResult, double currLatitude, double currLongitude, String name, String address, String metatag, int typeID, String timeOpen, String timeClose, String network, String postalCode, String description, String activeStatus, int choosenOptionID, String encodedImage) {
        Call call = RetrofitServices.retrofitRequest().update(APIBody.uploadBody(scanResult, currLatitude, currLongitude, name, address, metatag, typeID,
                timeOpen, timeClose, network, postalCode, description, activeStatus, choosenOptionID, encodedImage));
        formTerminalInteractor.initRetrofitUpdateTerminalAPI(call);
    }

    @Override
    public void onSuccessPostTerminalAPI(MainResponse mainResponse) {
        formTerminalView.setOnSuccessPostTerminalAPI(mainResponse);
    }

    @Override
    public void onFailedPostTerminalAPI(String message) {
        formTerminalView.setOnFailedPostTerminalAPI(message);
    }

    @Override
    public void onFailurePostTerminalAPI(String message) {
        formTerminalView.setOnFailurePostTerminalAPI(message);
    }

    @Override
    public void onSuccessUpdateTerminalAPI(MainResponse mainResponse) {
        formTerminalView.setOnSuccessUpdateTerminalAPI(mainResponse);
    }

    @Override
    public void onFailedUpdateTerminalAPI(String message) {
        formTerminalView.setOnFailedUpdateTerminalAPI(message);
    }

    @Override
    public void onFailureUpdateTerminalAPI(String message) {
        formTerminalView.setOnFailureUpdateTerminalAPI(message);
    }
}
