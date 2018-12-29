package template.solainteractive.com.androidsolatemplate.view;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;
import java.util.Calendar;
import android.widget.TimePicker;

import template.solainteractive.com.androidsolatemplate.R;

/**
 * Created by shermand on 19/03/18.
 */


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{


    static TimePickerFragment newInstance1(int hour, int minute)
    {
        Bundle args = new Bundle();
        args.putInt("hour", hour);
        args.putInt("minute", minute);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        EditText et = getActivity().findViewById(R.id.etTerminalOpen);

        if(et.getText().toString().isEmpty()){
            final Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }else{
            Bundle args = getArguments();

            final Calendar cal = Calendar.getInstance();
            int hour = args.getInt("hour", cal.get(Calendar.HOUR_OF_DAY));
            int minute = args.getInt("minute", cal.get(Calendar.MINUTE));
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            EditText et = getActivity().findViewById(R.id.etTerminalOpen);
            et.getText().clear();
            et.setText(et.getText()+(String.format("%02d:%02d", hourOfDay, minute)));
    }

}
