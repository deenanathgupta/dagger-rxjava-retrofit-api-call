package com.android.cognizantcodingtask.model

import android.annotation.SuppressLint
import com.android.cognizantcodingtask.data.common.BaseTModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
data class NewsFeedItem(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("imageHref") val imageUrl: String
) : BaseTModel() {

}
