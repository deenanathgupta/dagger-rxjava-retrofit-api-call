package com.android.cognizantcodingtask.network;

import com.android.cognizantcodingtask.app.CZApplication;
import com.android.cognizantcodingtask.utils.Logger;
import com.android.cognizantcodingtask.utils.UtilsHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


public class RestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        /*Modifying actual request and adding headers requesred*/
        Request modifiedRequest = originalRequest.newBuilder()
                .method(originalRequest.method(), originalRequest.body())
                .header(RestConstants.KEY_ACCEPT_ENCODING, RestConstants.VALUE_IDENTITY)
                .header(RestConstants.KEY_USER_AGENT, CZApplication.Companion.getUserAgent())
                .header(RestConstants.KEY_DEVICE_ID, UtilsHelper.Companion.getDeviceId())
                .build();

        RequestBody rb = originalRequest.body();
        Buffer buffer = new Buffer();
        if (rb != null)
            rb.writeTo(buffer);

        /*Logging modified and actual request which will hit server*/
        Logger.d(getClass().getName(), RestConstants.NETWORK_REQUEST_PAYLOAD + modifiedRequest.toString());
        Logger.d(getClass().getName(), RestConstants.VALUE_PAYLOAD + buffer.readUtf8() + "\n ::::Headers" + modifiedRequest.headers().toString());

        Response response = chain.proceed(modifiedRequest);

        final String responseString = new String(response.body().bytes());
        Logger.d(getClass().getName(), RestConstants.NETWORK_RESPONSE_PAYLOAD + responseString);

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), responseString))
                .build();
    }
}
