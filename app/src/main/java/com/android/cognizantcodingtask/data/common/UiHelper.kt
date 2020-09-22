package com.android.cognizantcodingtask.data.common

import com.android.cognizantcodingtask.R
import com.android.cognizantcodingtask.app.CZApplication

class UiHelper(builder: UiHelperBuilder) {
    var showProgress: Boolean = false
    var showEmptyView: Boolean = false
    var messageOnProgressDialog:String
    private  var error: NetworkError? = null

    init {
        showProgress = builder.showProgress
        showEmptyView = builder.showEmptyView
        messageOnProgressDialog = builder.messageToDisplay
        this.error = builder.error
    }


    class UiHelperBuilder {
        internal var showProgress: Boolean = false
        internal var error: NetworkError? = null
        internal var showEmptyView: Boolean = false
        internal var messageToDisplay = CZApplication.applicationContext().getString(R.string.app_name)

        fun showProgress(show: Boolean): UiHelperBuilder {
            showProgress = show
            return this
        }

        fun showMessage(message:String): UiHelperBuilder {
            messageToDisplay = message
            return this
        }

        fun setError(error: NetworkError): UiHelperBuilder {
            this.error = error
            return this
        }

        fun setMbShowEmptyView(showEmptyView: Boolean): UiHelperBuilder {
            this.showEmptyView = showEmptyView
            return this
        }

        fun build(): UiHelper {
            return UiHelper(this)
        }

    }
}