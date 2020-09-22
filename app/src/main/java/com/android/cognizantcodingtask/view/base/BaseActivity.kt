package com.android.cognizantcodingtask.view.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.android.cognizantcodingtask.R
import com.android.cognizantcodingtask.data.event.ShowSnackBarEvent
import com.android.cognizantcodingtask.utils.FragmentConstants
import com.android.cognizantcodingtask.utils.FragmentUtils
import com.android.cognizantcodingtask.utils.Logger
import com.android.cognizantcodingtask.utils.NavigationUtils
import com.android.cognizantcodingtask.utils.rxbus.RxBus
import com.android.cognizantcodingtask.utils.rxbus.RxBusCallback
import com.android.cognizantcodingtask.utils.rxbus.RxBusHelper
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), RxBusCallback {
    protected val TAG = BaseActivity::class.java.simpleName

    @Inject
    protected lateinit var rxBus: RxBus

    @Inject
    protected lateinit var appNavigator: NavigationUtils

    private var rxBusHelper: RxBusHelper? = null

    protected val baseDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObservingLiveData()
    }


    private fun getRootView(): View? {
        var rootView: View? = null
        try {
            val contentViewGroup = findViewById<ViewGroup>(android.R.id.content)
            rootView = null

            if (contentViewGroup != null)
                rootView = contentViewGroup.getChildAt(0)

            if (rootView == null)
                rootView = getWindow().getDecorView().getRootView()
        } catch (e: Exception) {
        }

        return rootView
    }

    private fun showSnackBar(snackBarEvent: ShowSnackBarEvent) {
        if (getRootView() != null) {
            val snackBar =
                Snackbar.make(getRootView()!!, snackBarEvent.message, snackBarEvent.length!!)
            snackBar.setAction(snackBarEvent.actionText) {
                //                snackBarActionClicked(snackBarEvent.requestCode)
            }
            snackBar.show()
        }
    }

    protected fun addFragment(
        args: Bundle? = null,
        fragmentType: Int,
        transType: Int, @IdRes containerId: Int,
        addAnimation: Boolean
    ) {
        if (isFinishing) return
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        var fragment: Fragment? = null
        if (fragment == null) {
            fragment = FragmentUtils.getFragmentTag(fragmentType)

            args?.run {
                fragment?.arguments = args
            }
            if (addAnimation) {
                ft.setCustomAnimations(
                    R.anim.animation_slide_in_up,
                    R.anim.animation_slide_out_down
                )
            }

            when (transType) {
                FragmentConstants.FRAG_ADD -> {
                    ft.add(containerId, fragment!!, fragment::class.java.simpleName)
                }
                FragmentConstants.FRAG_REPLACE -> {
                    ft.replace(containerId, fragment!!, fragment::class.java.simpleName)
                }
                FragmentConstants.FRAG_ADD_WITH_STACK -> {
                    ft.add(containerId, fragment!!, fragment::class.java.simpleName)
                    ft.addToBackStack(fragment::class.java.simpleName)
                }
                FragmentConstants.FRAG_REPLACE_WITH_STACK -> {
                    ft.replace(containerId, fragment!!, fragment::class.java.simpleName)
                    ft.addToBackStack(fragment::class.java.simpleName)
                }
            }
        } else {
            ft.attach(fragment)
        }
        ft.commitAllowingStateLoss()
        fm.executePendingTransactions()
    }

    override fun onResume() {
        super.onResume()
        registerEvents()
        Logger.d("OnResume")
    }

    override fun onPause() {
        super.onPause()
        unregisterEvents()
        Logger.d("OnPause")
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


    override fun onDestroy() {
        baseDisposable.clear()
        super.onDestroy()
    }

    open fun btnClick(view: View) {}


    override fun onBackPressed() {

        val fragmentList = supportFragmentManager.fragments

        var handled = false
        for (f in fragmentList) {
            if (f is BaseFragment) {
                handled = f.onBackPressed()

                if (handled) {
                    break
                }
            }
        }
        if (!handled) {
            super.onBackPressed()
        }
    }

}