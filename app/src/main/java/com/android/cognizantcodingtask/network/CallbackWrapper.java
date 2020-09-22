package com.android.cognizantcodingtask.network;

import com.android.cognizantcodingtask.R;
import com.android.cognizantcodingtask.app.CZApplication;
import com.android.cognizantcodingtask.data.common.NetworkError;
import com.android.cognizantcodingtask.utils.Logger;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class CallbackWrapper<T> extends DisposableObserver<Response<T>> {

    protected abstract void onSuccess(T response);

    protected abstract void onFailure(NetworkError error);

    @Override
    public void onNext(Response<T> response) {
        if (response.isSuccessful() && response.body() != null) {
            onSuccess(response.body());
        } else {
            Logger.d("On ERROR" + response.message());
            NetworkError error = new NetworkError();
            error.setErrorMessage(response.message());
            error.setStatusCode(response.code());
            onFailure(error);
        }
    }

    @Override
    public void onError(Throwable e) {
        NetworkError error = new NetworkError();
        error.setErrorCode("1");
        Logger.d("On ERROR" + e.toString());
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            error.setErrorMessage(getErrorMessage(responseBody));
            error.setStatusCode(((HttpException) e).code());
        } else if (e instanceof SocketTimeoutException) {
            error.setErrorMessage(CZApplication.Companion.applicationContext().getString(R.string.request_time_out));
        } else if (e instanceof IOException) {
            error.setErrorMessage(CZApplication.Companion.applicationContext().getString(R.string.network_error_heading));
        } else {
            error.setErrorMessage(CZApplication.Companion.applicationContext().getString(R.string.network_error_message_train));
        }
        onFailure(error);
    }

    @Override
    public void onComplete() {

    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}