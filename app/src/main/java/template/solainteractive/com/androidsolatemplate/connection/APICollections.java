package template.solainteractive.com.androidsolatemplate.connection;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import template.solainteractive.com.androidsolatemplate.Constants;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.model.RateModel;
import template.solainteractive.com.androidsolatemplate.model.TerminalModel;

/**
 * Created by BillySaputra on 22-Aug-17.
 */

public interface APICollections {
    // FOR EXAMPLE
    /*@POST(Constants.URL_API.ADD_FAVORITE)
    Call<MainResponse> addFavorite(@Body Map<String, String> body);*/


    // POST API
    @POST(Constants.URL_API.UPLOAD)
    Call<MainResponse> upload(@Body Map<String, String> body);

    @POST(Constants.URL_API.UPDATE)
    Call<MainResponse> update(@Body Map<String, String> body);

    @POST(Constants.URL_API.POST_LOGIN)
    Call<MainResponse> login(@Body Map<String, String> body);

    @POST(Constants.URL_API.POST_LOGOUT)
    Call<MainResponse> logout(@Body Map<String, String> body);

    @POST(Constants.URL_API.CHANGE_PASSWORD)
    Call<MainResponse> changePassword(@Body Map<String,String> body);

    @POST(Constants.URL_API.FORGET_PASSWORD)
    Call<MainResponse> forgetPassword(@Body Map<String, String> body);

    @POST(Constants.URL_API.DELETE)
    Call<MainResponse> delete(@Body Map<String, String> body);

    // GET API
    @GET(Constants.URL_API.GET_TERMINAL_URL)
    Call<TerminalModel> getTerminal();

    @GET(Constants.URL_API.GET_RATE_LIST)
    Call<RateModel> getRate();

    @POST(Constants.URL_API.TERMINAL_SEARCH)
    Call<TerminalModel> searchTerminal(@Body Map<String, String> body);
}
