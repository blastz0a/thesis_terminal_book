package template.solainteractive.com.androidsolatemplate.Presenter.MapList;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import retrofit2.Call;
import template.solainteractive.com.androidsolatemplate.Contract.MapListContract;
import template.solainteractive.com.androidsolatemplate.connection.APIBody;
import template.solainteractive.com.androidsolatemplate.connection.RetrofitServices;
import template.solainteractive.com.androidsolatemplate.model.Terminal;
import template.solainteractive.com.androidsolatemplate.model.TerminalModel;

public class MapListPresenter implements MapListContract.Presenter {

    private MapListContract.View mapListView;
    private MapListContract.Interactor mapListInteractor;

    public MapListPresenter(MapListContract.View mapListView){
        this.mapListView = mapListView;
        this.mapListInteractor = new MapListInteractor(this);
    }

    @Override
    public void getTerminalList() {
        Call call = RetrofitServices.retrofitRequest().getTerminal();
        mapListInteractor.initRetrofitGetTerminalCall(call);
    }

    @Override
    public void showTerminalResult(List<Terminal> terminalList) {
        if(terminalList.size() == 0){
            mapListView.showToast();
        } else {
            for(int i = 0; i < terminalList.size(); i++){
                LatLng latLng = new LatLng(terminalList.get(i).getTerminalLatitude(), terminalList.get(i).getTerminalLongitude());
                System.out.println("LatLng : " + latLng);
                mapListView.showTerminalMarker(i, latLng, terminalList.get(i).getTerminalActiveStatus());
            }
        }

    }

    @Override
    public void onSuccessGetTerminalAPI(TerminalModel terminalModel, List<Terminal> terminalList) {
        mapListView.setOnSuccessGetTerminalList(terminalList);
    }

}
