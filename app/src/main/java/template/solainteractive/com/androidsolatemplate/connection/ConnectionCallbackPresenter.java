package template.solainteractive.com.androidsolatemplate.connection;

import retrofit2.Call;
import retrofit2.Response;

public interface ConnectionCallbackPresenter {

    void onSuccessResponse(Call call, Response response);

    void onFailedResponse(Call call, Response response, String message);

    void onFailure(Call call, String message);
}
