package com.mdlink.api;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.mdlink.util.Constants;

public class RestAPIClent {

    public static final String BASE_URL = Constants.BASE_URL;
    private static Retrofit retrofit = null;
    private static String TAG = "RestAPIClient";

    public static Retrofit getClient() {

        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient().newBuilder();
        builder.readTimeout(125, TimeUnit.SECONDS);
        builder.connectTimeout(60, TimeUnit.SECONDS);

        builder.addInterceptor(logging);

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();

                Log.i(TAG,">>request>>"+request.toString());

                Response response = chain.proceed(request);

                return response;
            }
        });

        final okhttp3.OkHttpClient httpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        return retrofit;
    }
}
