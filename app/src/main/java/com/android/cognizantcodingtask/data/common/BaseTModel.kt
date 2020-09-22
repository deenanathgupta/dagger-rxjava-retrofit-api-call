package com.android.cognizantcodingtask.data.common

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable


open class BaseTModel : BaseObservable, Parcelable {
    var layoutResId: Int = 0
        set

    constructor() {}

    constructor(layoutResId: Int) {
        this.layoutResId = layoutResId
    }


    protected constructor(`in`: Parcel) {
        layoutResId = `in`.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(layoutResId)
    }

    companion object {

        @SuppressLint("ParcelCreator")
        val CREATOR: Parcelable.Creator<BaseTModel> = object : Parcelable.Creator<BaseTModel> {
            override fun createFromParcel(`in`: Parcel): BaseTModel {
                return BaseTModel(`in`)
            }

            override fun newArray(size: Int): Array<BaseTModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}
