package com.android.cognizantcodingtask.model

import android.annotation.SuppressLint
import com.android.cognizantcodingtask.data.common.BaseTModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
data class NewsFeedData(
    @SerializedName("title") val title: String,
    @SerializedName("rows") val newsList: ArrayList<NewsFeedItem>
) : BaseTModel()