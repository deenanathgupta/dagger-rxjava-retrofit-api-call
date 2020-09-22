package com.android.cognizantcodingtask.network;

import android.content.Context;

import com.android.cognizantcodingtask.app.CZApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestApiClient {
    private static RestApiClient instance;
    private Context mContext;
    private Retrofit retrofit;
    private String mBaseUrl;
    private List<Interceptor> interceptorList;
    private Gson gson;

    /**
     * Private constructor so that now one would be able to create its instance
     */
    public RestApiClient() {
    }

    /**
     * Creating static instance for consuming api service without context reference
     * Using Application context {@link com.android.cognizantcodingtask.app.CZApplication}
     */

    public static RestApiClient getApiClient() {
        if (instance != null)
            return instance;
        else
            throw new RuntimeException("Client cannot be null, Please setup client by calling::: new RestApiClient.ApiClientBuilder()");
    }

    private RestApiClient(ApiClientBuilder apiClientBuilder) {
        this.mContext = apiClientBuilder.mContext;
        this.mBaseUrl = apiClientBuilder.mBaseUrl;
        this.interceptorList = apiClientBuilder.interceptorList;
        this.gson = apiClientBuilder.gson;
        clientCreation(mContext);
    }

    /**
     * Retrofit adapter setup for client creation to call rest apis
     */
    private void clientCreation(Context context) {
//        this.mContext = context;

        //Gson builder for client
        if (gson == null)
            this.gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

        //Setting interceptor if custom interceptor not being passed
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //Logger
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        //Adding customized interceptors if passed any
        if (interceptorList != null && interceptorList.size() > 0) {
            for (Interceptor interceptor : interceptorList)
                builder.networkInterceptors().add(interceptor);
        } else {
            //Adding default interceptor
            builder.networkInterceptors().add(new RestInterceptor());
        }
        builder.addInterceptor(logging);

        //Building of ok http client with custom interceptors
        OkHttpClient okHttpClient = builder.build();


        //Base url setting if not set by builder will be picked automatically from current application
        if (mBaseUrl == null)
            this.mBaseUrl = CZApplication.Companion.getBaseUrl();

        retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * Custom Builder to create rest client
     */
    public static class ApiClientBuilder {
        private Context mContext;
        private String mBaseUrl;
        private List<Interceptor> interceptorList;
        private Gson gson;
        private boolean resetClient;

        public ApiClientBuilder context(Context context) {
            this.mContext = context;
            return this;
        }

        public ApiClientBuilder baseUrl(String baseUrl) {
            this.mBaseUrl = baseUrl;
            return this;
        }

        public ApiClientBuilder okHttpClient(List<Interceptor> interceptor) {
            this.interceptorList = interceptor;
            return this;
        }

        public ApiClientBuilder converter(Gson gson) {
            this.gson = gson;
            return this;
        }

        public ApiClientBuilder resetClient(boolean resetClient) {
            this.resetClient = resetClient;
            return this;
        }

        public RestApiClient build() {
            if (instance == null || resetClient) {
                instance = new RestApiClient(this);
                return instance;
            } else return instance;
        }
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
