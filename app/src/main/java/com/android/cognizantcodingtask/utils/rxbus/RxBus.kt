package com.android.cognizantcodingtask.utils.rxbus

import io.reactivex.Observable

interface RxBus {
    fun send(event: Any)

    fun toObservable(): Observable<Any>
}