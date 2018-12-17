package template.solainteractive.com.androidsolatemplate.connection;

/**
 * Created by BillySaputra on 22-Aug-17.
 */

public class APIConnectionHandler {
    private boolean isShowErrorDialog, isShowProgressDialog = true;

    /**
     * SET ERROR DIALOG
     * @return
     */
    protected boolean isShowErrorDialog() {
        return isShowErrorDialog;
    }

    public void setShowErrorDialog() {
        isShowErrorDialog = true;
    }

    /**
     * SET PROGRESS DIALOG
     * @return
     */

    protected boolean isShowProgressDialog() {
        return isShowProgressDialog;
    }

    public void dontShowProgressDialog() {
        isShowProgressDialog = false;
    }
}
