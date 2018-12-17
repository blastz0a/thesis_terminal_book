package template.solainteractive.com.androidsolatemplate.Contract;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import retrofit2.Call;
import template.solainteractive.com.androidsolatemplate.base.BaseView;
import template.solainteractive.com.androidsolatemplate.model.Terminal;
import template.solainteractive.com.androidsolatemplate.model.TerminalModel;

public interface MapListContract {

    interface View extends BaseView{
        void setOnSuccessGetTerminalList(List<Terminal> terminalList);
        void showTerminalMarker(int index, LatLng latLng, String activeStatus);
        void showToast();
    }

    interface Presenter{
        void getTerminalList();
        void showTerminalResult(List<Terminal> terminalList);

        void onSuccessGetTerminalAPI(TerminalModel terminalModel, List<Terminal> terminalList);
    }

    interface Interactor{
        void initRetrofitGetTerminalCall(Call call);
    }
}
