package template.solainteractive.com.androidsolatemplate.SignIn;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import template.solainteractive.com.androidsolatemplate.Contract.ForgetPasswordContract;
import template.solainteractive.com.androidsolatemplate.Contract.FormTerminalContract;
import template.solainteractive.com.androidsolatemplate.Presenter.SignIn.ForgetPasswordPresenter;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;

import static org.mockito.Mockito.verify;

public class ForgotPasswordPresenterTest {
    private ForgetPasswordPresenter presenter;
    private MainResponse mainResponse;

    @Mock
    ForgetPasswordContract.View forgotPasswordView;

    @Before
    public void setUpForgetPasswordTest(){
        MockitoAnnotations.initMocks(this);
        presenter = new ForgetPasswordPresenter(forgotPasswordView);
    }

    @Test
    public void setUpViewTest(){
        presenter.onSetUpView();
        verify(forgotPasswordView).setUpView();
    }

    @Test
    public void onSuccessForgetPasswordTest(){
        presenter.onSuccessForgetPassword(mainResponse);
        verify(forgotPasswordView).setOnSuccessForgetPassword();
    }

    @Test
    public void onFailedForgetPasswordTest(){
        presenter.onFailedForgetPassword("FAILED RESPONSE");
        verify(forgotPasswordView).setOnFailedForgetPassword("FAILED RESPONSE");
    }
}
