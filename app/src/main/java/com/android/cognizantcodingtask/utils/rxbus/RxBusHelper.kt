package com.android.cognizantcodingtask.utils.rxbus

import com.android.cognizantcodingtask.utils.Logger
import io.reactivex.observers.DisposableObserver


class RxBusHelper {
    private var disposable: DisposableObserver<Any>? = null

    fun registerEvents(rxBus: RxBus?, tag: String, callback: RxBusCallback) {

        if (rxBus != null) {
            disposable = object : DisposableObserver<Any>() {
                override fun onNext(event: Any) {
                    callback.onEventTrigger(event)
                }

                override fun onError(e: Throwable) {
                    Logger.d(tag, "Event : onError : " + e.message)
                }

                override fun onComplete() {
                    Logger.d(tag, "Event : onComplete")
                }
            }
            rxBus.toObservable().share().subscribeWith(disposable)
        }
    }

    fun unSubScribe() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
            disposable = null
        }
    }
}