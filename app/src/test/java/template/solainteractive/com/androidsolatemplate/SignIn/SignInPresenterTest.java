package template.solainteractive.com.androidsolatemplate.SignIn;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import template.solainteractive.com.androidsolatemplate.Contract.SigninContract;
import template.solainteractive.com.androidsolatemplate.Presenter.SignIn.SigninPresenter;

import static org.mockito.Mockito.verify;

public class SignInPresenterTest {
    private SigninPresenter presenter;
    private String message = "THIS IS RESPONSE";

    @Mock
    private SigninContract.View signInView;

    @Before
    public void setUpSignInTest(){
        MockitoAnnotations.initMocks(this);
        presenter = new SigninPresenter(signInView);
    }

    @Test
    public void onFailedPostLoginAPITest(){
        presenter.onFailedPostLoginAPI(message);
        verify(signInView).setOnFailedPostLoginAPI(message);
    }

    @Test
    public void onFailurePostLoginAPITest(){
        presenter.onFailurePostLoginAPI(message);
        verify(signInView).setOnFailurePostLoginAPI(message);
    }

}
