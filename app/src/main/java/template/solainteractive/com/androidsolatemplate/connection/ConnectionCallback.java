package template.solainteractive.com.androidsolatemplate.connection;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by BillySaputra on 23-Aug-17.
 */

public interface ConnectionCallback <T extends Object>{

    void onSuccessResponse(Call<T> call, Response<T> response);

    void onFailedResponse(Call<T> call, Response<T> response, String message);

    void onFailure(String message);
}
