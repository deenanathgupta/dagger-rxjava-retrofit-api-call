package com.android.cognizantcodingtask.view.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.android.cognizantcodingtask.R
import com.android.cognizantcodingtask.view.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @JvmField
    @Rule
    val mActivityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, false, true)

    private lateinit var mViewModel: HomeViewModel

    @Before
    fun setup() {
        mViewModel = mActivityRule.activity.getViewModel()
    }

    @Test
    fun verify_showNewsData() {
        Thread.sleep(5000)
        onView(ViewMatchers.withId(R.id.recycler_news_list))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(
                matches(
                    ViewMatchers.hasMinimumChildCount(1)
                )
            )
    }

    @Test
    fun verify_showErrorToast() {
        mViewModel.showSnackBarAction("networkError", "Okay", 0, Snackbar.LENGTH_LONG)
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("networkError")))
    }
}
