package template.solainteractive.com.androidsolatemplate.Terminal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import template.solainteractive.com.androidsolatemplate.Contract.FormTerminalContract;
import template.solainteractive.com.androidsolatemplate.Presenter.Terminal.FormTerminalPresenter;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.model.RateModel;

import static org.mockito.Mockito.verify;

public class FormTerminalPresenterTest {
    private FormTerminalPresenter presenter;
    private MainResponse mainResponse;

    @Mock
    private FormTerminalContract.View formTerminalView;

    @Before
    public void setFormTerminalShowTest(){
        MockitoAnnotations.initMocks(this);
        presenter = new FormTerminalPresenter(formTerminalView);
    }

    @Test
    public void showLocationTest(){
        presenter.showLocation();
        verify(formTerminalView).setShowLocation();
    }

    @Test
    public void loadAvatarImageTest(){
        presenter.loadAvatarImage();
        verify(formTerminalView).setLoadAvatarImage();
    }

    @Test
    public void btnScanClickTest(){
        presenter.btnScanClick();
        verify(formTerminalView).setBtnScanClick();
    }

    @Test
    public void btnMapClickTest(){
        presenter.btnMapClick();
        verify(formTerminalView).setBtnMapClick();
    }

    @Test
    public void onSuccessGetRateAPITest(){
        RateModel rateModel = new RateModel();
        presenter.onSuccessGetRateAPI(rateModel);
        verify(formTerminalView).setOnSuccessGetRateAPI(rateModel);
    }

    @Test
    public void onFailedGetRateAPITest(){
        presenter.onFailedGetRateAPI("RESPONSE");
        verify(formTerminalView).setOnFailedGetRateAPI("RESPONSE");
    }

    @Test
    public void onSuccessPostTerminalAPITest(){
        presenter.onSuccessPostTerminalAPI(mainResponse);
        verify(formTerminalView).setOnSuccessPostTerminalAPI(mainResponse);
    }

    @Test
    public void onFailedPostTerminalAPITest(){
        presenter.onFailedPostTerminalAPI("RESPONSE");
        verify(formTerminalView).setOnFailedPostTerminalAPI("RESPONSE");
    }

    @Test
    public void onFailurePostTerminalAPITest(){
        presenter.onFailurePostTerminalAPI("RESPONSE");
        verify(formTerminalView).setOnFailurePostTerminalAPI("RESPONSE");
    }

    @Test
    public void onSuccessUpdateTerminalAPITest(){
        presenter.onSuccessUpdateTerminalAPI(mainResponse);
        verify(formTerminalView).setOnSuccessUpdateTerminalAPI(mainResponse);
    }

    @Test
    public void onFailedUpdateTerminalAPITest(){
        presenter.onFailedUpdateTerminalAPI("RESPONSE");
        verify(formTerminalView).setOnFailedUpdateTerminalAPI("RESPONSE");
    }

    @Test
    public void onFailureUpdateTerminalAPITest(){
        presenter.onFailureUpdateTerminalAPI("RESPONSE");
        verify(formTerminalView).setOnFailureUpdateTerminalAPI("RESPONSE");
    }
}
