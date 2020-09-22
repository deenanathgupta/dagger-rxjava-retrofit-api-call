package com.android.cognizantcodingtask.view.base

import com.android.cognizantcodingtask.data.event.ShowSnackBarEvent
import com.android.cognizantcodingtask.network.RestService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

open class BaseNetworkViewModel(private var inRestService: RestService) : BaseViewModel() {

    protected var compositeDisposable: CompositeDisposable? = null
    protected val networkConnectionObservable: PublishSubject<Boolean> = PublishSubject.create()

    val showSnackBarEventObservable: PublishSubject<ShowSnackBarEvent> = PublishSubject.create()

    init {
        compositeDisposable = CompositeDisposable()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.clear()
    }

    fun getRestService(): RestService {
        return this.inRestService
    }

    fun notifyConnectionChanged(connection: Boolean) {
        networkConnectionObservable.onNext(connection)
    }

    fun showSnackBarAction(message: String, actionText: String, requestCode: Int, length: Int?) {
        showSnackBarEventObservable.onNext(ShowSnackBarEvent(message, actionText, requestCode, length))
    }

    open fun snackBarActionClicked(requestCode: Int) {

    }

}