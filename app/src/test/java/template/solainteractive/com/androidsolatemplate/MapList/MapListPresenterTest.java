package template.solainteractive.com.androidsolatemplate.MapList;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import template.solainteractive.com.androidsolatemplate.Contract.MapListContract;
import template.solainteractive.com.androidsolatemplate.Presenter.MapList.MapListPresenter;
import template.solainteractive.com.androidsolatemplate.model.Terminal;
import template.solainteractive.com.androidsolatemplate.model.TerminalModel;

import static org.mockito.Mockito.verify;

public class MapListPresenterTest {
    private MapListPresenter presenter;
    private List<Terminal> listTerminal = new ArrayList<>();

    @Mock
    MapListContract.View mapListView;

    @Before
    public void setUpMapListView(){
        MockitoAnnotations.initMocks(this);
        presenter = new MapListPresenter(mapListView);
    }

    @Test
    public void showTerminalResultTest(){
        TerminalModel terminalModel = new TerminalModel();

        for (int i=0; i<10; i++){
            Terminal terminal = new Terminal();
            terminal.setTerminalActiveStatus("ACTIVE");
            terminal.setTerminalLatitude(-1000.200);
            terminal.setTerminalLongitude(110.223232);

            listTerminal.add(terminal);
        }
        terminalModel.setTerminalList(listTerminal);
        presenter.showTerminalResult(terminalModel.terminalList);
        if (terminalModel.terminalList.isEmpty()){
            verify(mapListView).showToast();
        }
        else{
            for (int y=0;y<listTerminal.size(); y++){
                LatLng latLng = new LatLng(listTerminal.get(y).getTerminalLatitude(), listTerminal.get(y).getTerminalLongitude());
                verify(mapListView).showTerminalMarker(y,latLng,listTerminal.get(y).getTerminalActiveStatus());
            }
        }
    }
}
