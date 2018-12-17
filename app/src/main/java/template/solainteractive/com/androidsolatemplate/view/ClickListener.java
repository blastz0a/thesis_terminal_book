package template.solainteractive.com.androidsolatemplate.view;

import android.view.View;

/**
 * Created by shermand on 28/03/18.
 */


public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}