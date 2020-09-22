package com.android.cognizantcodingtask.data.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta implements Parcelable {

    @SerializedName("statusCode")
    @Expose
    private String status_code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("error_Message")
    @Expose
    private String error_message;

    @SerializedName("success_Message")
    @Expose
    private String success_message;

    public String getSuccess_message() {
        return success_message;
    }

    public void setSuccess_message(String success_message) {
        this.success_message = success_message;
    }

    public String getStatus_code() {
        return status_code;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getError_message() {
        return error_message;
    }


    protected Meta(Parcel in) {
        status_code = in.readString();
        message = in.readString();
        status = in.readString();
        error_message = in.readString();
        success_message = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status_code);
        dest.writeString(message);
        dest.writeString(status);
        dest.writeString(error_message);
        dest.writeString(success_message);
    }

    @SuppressWarnings("unused")
    public static final Creator<Meta> CREATOR = new Creator<Meta>() {
        @Override
        public Meta createFromParcel(Parcel in) {
            return new Meta(in);
        }

        @Override
        public Meta[] newArray(int size) {
            return new Meta[size];
        }
    };
}