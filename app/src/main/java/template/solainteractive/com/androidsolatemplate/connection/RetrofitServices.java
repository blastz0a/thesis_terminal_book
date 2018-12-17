package template.solainteractive.com.androidsolatemplate.connection;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import template.solainteractive.com.androidsolatemplate.Constants;
import template.solainteractive.com.androidsolatemplate.MyApplication;

/**
 * Created by BillySaputra on 22-Aug-17.
 */

public class RetrofitServices {
    private static OkHttpClient.Builder okHttpClientBuilder;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private static APICollections restApi;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Constants.URL_API.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static APICollections retrofitLoginRequest(){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.readTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS);
        okHttpClient.retryOnConnectionFailure(true);
        addInterceptorLoginOkHTTP(okHttpClient);

        OkHttpClient client = okHttpClient.build();
        Retrofit retrofit = builder.client(client).build();

        return retrofit.create(APICollections.class);
    }

    public static APICollections retrofitRequest(){
        Log.i("RETROFIT", "REST API");
        if(okHttpClientBuilder == null) {
            okHttpClientBuilder = new OkHttpClient.Builder();
        }

        okHttpClientBuilder.readTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS);
        okHttpClientBuilder.connectTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS);
        okHttpClientBuilder.retryOnConnectionFailure(true);
        addInterceptorOkHTTP();

        if(okHttpClient==null) {
            okHttpClient = okHttpClientBuilder.build();
        }

        if(retrofit == null) {
            retrofit = builder.client(okHttpClient).build();
        }

        if(restApi == null) {
            restApi = retrofit.create(APICollections.class);
        }

        return restApi;
    }

    public static void addInterceptorOkHTTP(){
        if(okHttpClientBuilder==null){
            retrofitRequest();
            return;
        }
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request;
                request = original.newBuilder()
                        .header("Token", MyApplication.getInstance().getAccessToken())
                        .header("User-Agent", "Android")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
    }

    public static void addInterceptorLoginOkHTTP(OkHttpClient.Builder client){
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request;
                request = original.newBuilder()
                        .header("User-Agent", "Android")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
    }
}
