package template.solainteractive.com.androidsolatemplate.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import template.solainteractive.com.androidsolatemplate.DateTimeUtils;
import template.solainteractive.com.androidsolatemplate.R;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_year)
    TextView tvYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        try {
            tvDate.setText(DateTimeUtils.getCurrentDate());
            tvDay.setText(DateTimeUtils.setDateToDay(DateTimeUtils.convertStringToDate(DateTimeUtils.getCurrentDate())));
            tvMonth.setText(DateTimeUtils.setDateToMonth(DateTimeUtils.convertStringToDate(DateTimeUtils.getCurrentDate())));
            tvYear.setText(DateTimeUtils.setDateToYear(DateTimeUtils.convertStringToDate(DateTimeUtils.getCurrentDate())));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
