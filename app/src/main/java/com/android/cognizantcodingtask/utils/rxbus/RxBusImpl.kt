package com.android.cognizantcodingtask.utils.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class RxBusImpl : RxBus {

    var bus: PublishSubject<Any> = PublishSubject.create()

    override fun send(event: Any) {
        bus.onNext(event)

    }

    override fun toObservable(): Observable<Any> {
        return bus
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }
}