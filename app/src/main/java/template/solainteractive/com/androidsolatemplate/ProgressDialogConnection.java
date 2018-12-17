package template.solainteractive.com.androidsolatemplate;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.view.Window;
import android.widget.ProgressBar;

/**
 * Created by BillySaputra on 23-Aug-17.
 */

public class ProgressDialogConnection {
    private Dialog progressDialog;

    public void showProgressDialog(Activity activity, boolean isShow){
        if (isShow){
            try {
                progressDialog = new Dialog(activity);
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.setContentView(R.layout.layout_progress_dialog);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);

                ProgressBar progressBar = progressDialog.findViewById(R.id.progress_bar);
                progressBar.getIndeterminateDrawable()
                        .setColorFilter(activity.getResources().getColor(R.color.color_green_500),
                                PorterDuff.Mode.SRC_IN);
                progressDialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void dismissProgressDialog(){
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
