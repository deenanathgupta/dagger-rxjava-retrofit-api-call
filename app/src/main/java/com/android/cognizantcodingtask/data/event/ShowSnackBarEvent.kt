package com.android.cognizantcodingtask.data.event

class ShowSnackBarEvent(
        val message: String,
        val actionText: String,
        val requestCode: Int,
        val length: Int? = 0)