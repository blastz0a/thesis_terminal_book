package template.solainteractive.com.androidsolatemplate.SignIn;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import template.solainteractive.com.androidsolatemplate.Contract.ChangePasswordContract;
import template.solainteractive.com.androidsolatemplate.Presenter.SignIn.ChangePasswordPresenter;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;

import static org.mockito.Mockito.verify;

public class ChangePasswordPresenterTest {
    private ChangePasswordPresenter presenter;
    private MainResponse mainResponse;

    @Mock
    ChangePasswordContract.View changePasswordView;

    @Before
    public void setUpChangePasswordTest(){
        MockitoAnnotations.initMocks(this);
        presenter = new ChangePasswordPresenter(changePasswordView);
    }

    @Test
    public void onValidateChangePasswordTest(){
        presenter.onValidateChangePassword();
        verify(changePasswordView).setOnValidateChangePassword();
    }

    @Test
    public void onSetUpViewTest(){
        presenter.onSetupView();
        verify(changePasswordView).setUpTypeFace();
        verify(changePasswordView).setUpToolbar();
    }

    @Test
    public void onSuccessChangePasswordAPITest(){
        presenter.onSuccessChangePasswordAPI(mainResponse);
        verify(changePasswordView).setOnSuccessChangePasswordAPI();
    }

    @Test
    public void onFailedChangePasswordTest(){
        presenter.onFailedChangePasswordAPI("FAILED RESPONSE");
        verify(changePasswordView).showSnackbar("FAILED RESPONSE");
    }
}
