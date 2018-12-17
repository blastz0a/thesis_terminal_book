package template.solainteractive.com.androidsolatemplate.model;

import com.google.gson.annotations.SerializedName;

import template.solainteractive.com.androidsolatemplate.Constants;

/**
 * Created by BillySaputra on 23-Aug-17.
 */

public class MainResponse {
    @SerializedName(Constants.APIObject.STATUS)
    String status;

    @SerializedName(Constants.APIObject.MESSAGE)
    String message;

    @SerializedName(Constants.APIObject.TOKEN)
    String accessToken;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
