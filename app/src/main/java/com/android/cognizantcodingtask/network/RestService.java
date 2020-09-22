package com.android.cognizantcodingtask.network;


import com.android.cognizantcodingtask.model.NewsFeedData;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RestService {
    @GET()
    Observable<Response<NewsFeedData>> getNewsFeed(@Url String url);
}
