package com.android.cognizantcodingtask.view.base

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.cognizantcodingtask.R
import com.android.cognizantcodingtask.app.CZApplication
import com.android.cognizantcodingtask.data.common.NetworkError
import com.android.cognizantcodingtask.data.common.UiHelper
import io.reactivex.subjects.PublishSubject

open class BaseViewModel : ViewModel() {

    var uiLiveData: MutableLiveData<UiHelper> = MutableLiveData()

    var bottomLoadingObservable: PublishSubject<Boolean> = PublishSubject.create<Boolean>()

    var progressBottom: ObservableField<Boolean> = ObservableField()

    protected fun showProgress() {
        uiLiveData.value = UiHelper.UiHelperBuilder()
            .showProgress(true)
            .build()
    }

    protected fun showProgress(message: String) {
        var msg = message
        if (message.isEmpty()) {
            msg = CZApplication.applicationContext().getString(R.string.please_wait)
        }
        uiLiveData.value = UiHelper.UiHelperBuilder()
            .showProgress(true)
            .showMessage(msg)
            .build()
    }


    protected fun hideProgress() {
        uiLiveData.value = UiHelper.UiHelperBuilder().showProgress(false).build()

    }

    protected fun setErrorCode(error: NetworkError) {
        val helper = UiHelper.UiHelperBuilder().setError(error).build()
        uiLiveData.value = helper
    }


}