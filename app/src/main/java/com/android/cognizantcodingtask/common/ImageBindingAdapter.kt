package com.android.cognizantcodingtask.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.cognizantcodingtask.R
import com.squareup.picasso.Picasso


object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:newsImage")
    fun setImageUrl(view: ImageView, url: String?) {
        url?.let {
            val aUrl = it!!.replace("http", "https")
            Picasso.with(view.context)
                .load(aUrl)
                .placeholder(R.drawable.icn_image_placeholder)
                .into(view);
        }

    }
}
