package template.solainteractive.com.androidsolatemplate.Utils;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.view_interface.SnackBarOnClick;

/**
 * Created by BillySaputra on 23-Aug-17.
 */

public class Utils {
    public static void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        snackbar.show();
    }

    public static void showInfiniteSnackBar(View view, String message, String button, final SnackBarOnClick snackBarOnClick) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        snackbar.setActionTextColor(Color.RED);
        snackbar.setAction(button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBarOnClick.onSnackBarClick();
            }
        });

        snackbar.show();
    }
    public static void intentWithClearTask(AppCompatActivity mActivity, Class<?> classDestination){
        Intent intent = new Intent(mActivity, classDestination);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        overridePendingTransition(mActivity);
        mActivity.startActivity(intent);
    }
    private static void overridePendingTransition(AppCompatActivity mActivity) {
        mActivity.overridePendingTransition(0, 0);
    }

    public static void setupAppToolbarForActivity(final AppCompatActivity mActivity, Toolbar toolbar, String title) {
        TextView tvTitle = mActivity.findViewById(R.id.tvToolbar);
        tvTitle.setText(title);
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
