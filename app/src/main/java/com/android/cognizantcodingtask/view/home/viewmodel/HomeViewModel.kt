package com.android.cognizantcodingtask.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.android.cognizantcodingtask.data.common.NetworkError
import com.android.cognizantcodingtask.model.NewsFeedData
import com.android.cognizantcodingtask.network.CallbackWrapper
import com.android.cognizantcodingtask.network.RestService
import com.android.cognizantcodingtask.view.base.BaseNetworkViewModel
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(restService: RestService) :
    BaseNetworkViewModel(restService) {
    var newsData: MutableLiveData<NewsFeedData> = MutableLiveData<NewsFeedData>()

    fun getNewsFeedData() {
        getRestService().getNewsFeed("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CallbackWrapper<NewsFeedData>() {
                override fun onSuccess(t: NewsFeedData) {
                    newsData.value = t
                }

                override fun onFailure(error: NetworkError) {
                    showSnackBarAction(error.errorMessage!!, "Okay", 0, Snackbar.LENGTH_LONG)

                }
            })
    }
}