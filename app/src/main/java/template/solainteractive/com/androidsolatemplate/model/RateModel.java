package template.solainteractive.com.androidsolatemplate.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shermand on 16/04/18.
 */

public class RateModel extends MainResponse {
    @SerializedName("TERMINAL")
    public List<Rate> rateList;

    public List<Rate> getRateList() {
        return rateList;
    }

}
