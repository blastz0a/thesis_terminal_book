package template.solainteractive.com.androidsolatemplate.Contract;

import android.graphics.Bitmap;

import retrofit2.Call;
import template.solainteractive.com.androidsolatemplate.base.BaseView;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.model.RateModel;

public interface FormTerminalContract {

    interface View extends BaseView{
        void setShowLocation();
        void setLoadAvatarImage();
        void setBtnScanClick();
        void setBtnMapClick();

        void setOnSuccessGetRateAPI(RateModel rateModel);
        void setOnFailedGetRateAPI(String message);
        void setOnFailureGetRateAPI(String message);
        void setOnSuccessPostTerminalAPI(MainResponse mainResponse);
        void setOnFailedPostTerminalAPI(String message);
        void setOnFailurePostTerminalAPI(String message);
        void setOnSuccessUpdateTerminalAPI(MainResponse mainResponse);
        void setOnFailedUpdateTerminalAPI(String message);
        void setOnFailureUpdateTerminalAPI(String message);
    }

    interface Presenter{
        void showLocation();
        void loadAvatarImage();
        void btnScanClick();
        void btnMapClick();

        void getRateAPI();
        void postUploadTerminalAPI(String scanResult, double currLatitude, double currLongitude, String name, String address, String metatag, int typeID,
                             String timeOpen, String timeClose, String network, String postalCode, String description, String activeStatus, int choosenOptionID, String encodedImage);
        void postUpdateTerminalAPI(String scanResult, double currLatitude, double currLongitude, String name, String address, String metatag, int typeID,
                           String timeOpen, String timeClose, String network, String postalCode, String description, String activeStatus, int choosenOptionID, String encodedImage);

        void onSuccessGetRateAPI(RateModel rateModel);
        void onFailedGetRateAPI(String message);
        void onFailureGetRateAPI(String message);
        void onSuccessPostTerminalAPI(MainResponse mainResponse);
        void onFailedPostTerminalAPI(String message);
        void onFailurePostTerminalAPI(String message);
        void onSuccessUpdateTerminalAPI(MainResponse mainResponse);
        void onFailedUpdateTerminalAPI(String message);
        void onFailureUpdateTerminalAPI(String message);
    }

    interface Interactor{
        void initRetrofitGetRateAPI(Call call);
        void initRetrofitPostTerminalAPI(Call call);
        void initRetrofitUpdateTerminalAPI(Call call);
    }

}
