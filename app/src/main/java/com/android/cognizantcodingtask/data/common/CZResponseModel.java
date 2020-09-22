package com.android.cognizantcodingtask.data.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CZResponseModel<T> implements Parcelable {
    @SerializedName("meta_data")
    @Expose
    private Meta meta;
    
    @SerializedName("data")
    @Expose
    private T data;

    public Meta getMeta() {
        return meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CZResponseModel() {
    }

    protected CZResponseModel(Parcel in) {
        meta = (Meta) in.readValue(Meta.class.getClassLoader());
        String className = in.readString();
        if (className != null) {
            try {
                data = in.readParcelable(Class.forName(className).getClassLoader());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(meta);
        if (data != null) {
            dest.writeString(data.getClass().getName());
            dest.writeParcelable((Parcelable) data, flags);
        } else dest.writeString(null);
    }

    @SuppressWarnings("unused")
    public static final Creator<CZResponseModel> CREATOR = new Creator<CZResponseModel>() {
        @Override
        public CZResponseModel createFromParcel(Parcel in) {
            return new CZResponseModel(in);
        }

        @Override
        public CZResponseModel[] newArray(int size) {
            return new CZResponseModel[size];
        }
    };
}