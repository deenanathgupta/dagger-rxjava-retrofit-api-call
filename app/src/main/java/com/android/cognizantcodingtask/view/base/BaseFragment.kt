package com.android.cognizantcodingtask.view.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.android.cognizantcodingtask.data.event.ShowSnackBarEvent
import com.android.cognizantcodingtask.utils.UtilsHelper
import com.android.cognizantcodingtask.utils.rxbus.RxBus
import com.android.cognizantcodingtask.utils.rxbus.RxBusCallback
import com.android.cognizantcodingtask.utils.rxbus.RxBusHelper
import com.google.android.material.snackbar.Snackbar.make
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment(), RxBusCallback {

    protected val TAG = BaseFragment::class.java.simpleName

    @Inject
    protected lateinit var rxBus: RxBus

    private var rxBusHelper: RxBusHelper? = null

    protected var baseDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObservingLiveData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSetup()
    }

    fun hideKeyboard() {
        UtilsHelper.hideKeyboard(activity)
    }

    open fun btnClick(view: View) {}

    private fun initSetup() {
        registerEvents()
        baseDisposable = CompositeDisposable()
    }

    override fun onResume() {
        registerEvents()
        super.onResume()
    }

    override fun onPause() {
        unregisterEvents()
        hideKeyboard()
        super.onPause()
    }

    override fun onDestroyView() {
        baseDisposable?.clear()
        super.onDestroyView()
    }

    override fun onDestroy() {
        baseDisposable?.dispose()
        super.onDestroy()
    }

    private fun registerEvents() {
        if (rxBusHelper == null) {
            rxBusHelper = RxBusHelper()
            rxBusHelper?.registerEvents(rxBus, TAG, this)
        }
    }

    private fun unregisterEvents() {
        rxBusHelper?.unSubScribe()
        rxBusHelper = null
    }

    override fun onEventTrigger(event: Any) {
        handleEvents(event)
    }

    open fun handleEvents(event: Any) {
        //Sub class can override this method if required
    }

    open fun getViewModel(): BaseViewModel? {
        //Sub class can override this method if required
        return null
    }

    @CallSuper
    open fun startObservingLiveData() {
        (getViewModel() as? BaseNetworkViewModel)?.showSnackBarEventObservable?.subscribe { event ->
            showSnackBar(
                event
            )
        }?.let {
            baseDisposable?.add(it)
        }
    }

    private fun getRootView(): View? {
        var rootView: View? = null
        try {
            val contentViewGroup = activity!!.findViewById(android.R.id.content) as ViewGroup
            rootView = null

            if (contentViewGroup != null)
                rootView = contentViewGroup!!.getChildAt(0)

            if (rootView == null)
                rootView = activity!!.getWindow().getDecorView().getRootView()
        } catch (e: Exception) {
        }

        return rootView
    }

    private fun showSnackBar(snackBarEvent: ShowSnackBarEvent) {
        if (isAdded && activity is BaseActivity && getRootView() != null) {
            val snackBar = make(getRootView()!!, snackBarEvent.message, snackBarEvent.length!!)
            snackBar.setAction(snackBarEvent.actionText) {
                snackBarAction(snackBarEvent.requestCode)
            }
            snackBar.show()
        }
    }

    open fun snackBarAction(requestCode: Int) {


    }

    open fun onBackPressed(): Boolean {
        return false;
    }

}