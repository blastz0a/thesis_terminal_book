package template.solainteractive.com.androidsolatemplate.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.model.Rate;

/**
 * Created by shermand on 12/04/18.
 */

public class RateAdapter extends ArrayAdapter<Rate> {
    private Context context;
    private List<Rate> rateList;


    public RateAdapter(@NonNull Context context, int resource, List<Rate> rateList) {
        super(context, resource, rateList);
        this.context = context;
        this.rateList = rateList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return myCustomSpinnerView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(position == -1){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customDropDownView = layoutInflater.inflate(R.layout.spinner_item, parent, false);

            TextView tv1 = customDropDownView.findViewById(R.id.rate_id);
            TextView tv2 = customDropDownView.findViewById(R.id.rate_name);
            TextView tv3 = customDropDownView.findViewById(R.id.cap_id);

            tv1.setText("Choose Rate");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);

            return customDropDownView;
        }
        return myCustomSpinnerView(position, convertView, parent);
    }

    private View myCustomSpinnerView(int position, View view, ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.spinner_item, parent, false);

        TextView tv1 = customView.findViewById(R.id.rate_id);
        TextView tv2 = customView.findViewById(R.id.rate_name);
        TextView tv3 = customView.findViewById(R.id.cap_id);

        tv1.setText(String.valueOf(rateList.get(position).getRateId()));
        tv2.setText("Rp " + String.valueOf(rateList.get(position).getHourlyRate())+ "/hour");
//        tv3.setText("Cap " + String.valueOf(rateList.get(position).getDailyCap()));

        return customView;
    }
}

