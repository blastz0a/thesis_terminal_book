package template.solainteractive.com.androidsolatemplate.Terminal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import template.solainteractive.com.androidsolatemplate.Contract.ShowTerminalContract;
import template.solainteractive.com.androidsolatemplate.Presenter.Terminal.ShowTerminalPresenter;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.model.Terminal;
import template.solainteractive.com.androidsolatemplate.model.TerminalModel;

import static org.mockito.Mockito.verify;

public class ShowTerminalPresenterTest {
    private ShowTerminalPresenter presenter;
    private List<Terminal> listTerminal = new ArrayList<>();
    private MainResponse mainResponse;

    @Mock
    private ShowTerminalContract.View showTerminalView;

    @Before
    public void setUpShowTerminalTest(){
        MockitoAnnotations.initMocks(this);
        presenter = new ShowTerminalPresenter(showTerminalView);
    }

    @Test
    public void onClickTest(){
        TerminalModel terminalModel = new TerminalModel();

        for (int x=0; x<5; x++){
            Terminal terminal = new Terminal();

            listTerminal.add(new Terminal());
        }
        terminalModel.setTerminalList(listTerminal);
        presenter.onClick(terminalModel,listTerminal);
        verify(showTerminalView).dismissProgressDialog();
        verify(showTerminalView).setOnClick(terminalModel,listTerminal);
    }

    @Test
    public void onSuccessGetTerminalTest(){
        TerminalModel terminalModel = new TerminalModel();
        for (int i=0;i<10;i++){
            listTerminal.add(new Terminal());

        }
        terminalModel.setTerminalList(listTerminal);
        if(listTerminal.size() > 0){
            System.out.println("Check Terminal Data : " + listTerminal.get(0).getTerminalName());
        } else {
            System.out.println("size : " + listTerminal.size());
        }
        presenter.onSuccessGetTerminal(terminalModel,listTerminal);
        verify(showTerminalView).setAdapter(terminalModel,listTerminal);
        verify(showTerminalView).dismissProgressDialog();
    }

    @Test
    public void onFailedGetTerminalTest(){
        presenter.onFailedGetTerminal("RESPONSE ERROR");
        verify(showTerminalView).dismissProgressDialog();
        verify(showTerminalView).intentToSignInActivity("RESPONSE ERROR");
    }

    @Test
    public void onFailurGetTerminalTest(){
        presenter.onFailureGetTerminal("RESPONSE ERROR");
        verify(showTerminalView).dismissProgressDialog();
        verify(showTerminalView).showSnackbar();
    }

    @Test
    public void onFailurePostLogoutTest(){
        presenter.onFailurePostLogout("RESPONSE ERROR");
        verify(showTerminalView).showSnackbar();
    }

    @Test
    public void onSuccessDeleteTerminalTest(){
        presenter.onSuccessDeleteTerminal(mainResponse);
        verify(showTerminalView).dismissProgressDialog();
        verify(showTerminalView).getRefreshTerminalList();
    }
}
