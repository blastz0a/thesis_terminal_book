package template.solainteractive.com.androidsolatemplate.model;

import com.google.gson.annotations.SerializedName;

import template.solainteractive.com.androidsolatemplate.Constants;

/**
 * Created by shermand on 16/04/18.
 */

public class Rate {
    @SerializedName(Constants.APIObject.RATE_ID)
    int rateId;
    @SerializedName(Constants.APIObject.DAILY_CAP)
    int dailyCap;
    @SerializedName(Constants.APIObject.HOURLY_RATE)
    float hourlyRate;

    public int getRateId() {
        return rateId;
    }

    public float getHourlyRate(){
        return hourlyRate;
    }

    public int getDailyCap(){
        return dailyCap;
    }
}


