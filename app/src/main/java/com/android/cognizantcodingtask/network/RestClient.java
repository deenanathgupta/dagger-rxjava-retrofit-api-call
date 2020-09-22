package com.android.cognizantcodingtask.network;

import retrofit2.Retrofit;


public class RestClient {
    static RestService restService = null;

    /**
     * Api service creation goes here check if its null create new one
     */
    public static RestService getClient() {
        if (restService == null) {
            Retrofit retrofit = RestApiClient.getApiClient().getRetrofit();
            restService = retrofit.create(RestService.class);
        }
        return restService;
    }

}
