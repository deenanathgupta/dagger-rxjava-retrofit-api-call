package com.android.cognizantcodingtask.view.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.cognizantcodingtask.R
import com.android.cognizantcodingtask.common.BaseAdapter
import com.android.cognizantcodingtask.common.BaseHandler
import com.android.cognizantcodingtask.common.SpaceItemDecorator
import com.android.cognizantcodingtask.databinding.ActivityMainBinding
import com.android.cognizantcodingtask.model.NewsFeedItem
import com.android.cognizantcodingtask.utils.CZConstants
import com.android.cognizantcodingtask.view.base.BaseActivity
import com.android.cognizantcodingtask.view.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.toolbar_normal.*
import kotlinx.android.synthetic.main.toolbar_normal.view.txt_toolbar_title
import java.util.ArrayList
import javax.inject.Inject

class MainActivity : BaseActivity(), BaseHandler<NewsFeedItem> {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var adapter: BaseAdapter<NewsFeedItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        initToolbar(getToolbar())
        initView()
        progressLytVisibility(true)
    }

    /**
     * init recycler view¬
     */
    private fun initView() {
        binding.recyclerNewsList.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val itemDecoration = SpaceItemDecorator(this!!.baseContext, R.dimen.margin_5dp)
        binding.recyclerNewsList.addItemDecoration(itemDecoration)
        binding.swipeRefreshLayout.setOnRefreshListener {
            getNewsFeedData()

        }
    }

    /**
     * init toolbar
     */
    private fun initToolbar(toolbar: Toolbar?) {
        toolbar?.run {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            invalidateOptionsMenu()
            txt_toolbar_title.text = CZConstants.TITLE_HOME
        }
    }


    private fun getNewsFeedData() {
        getViewModel()?.getNewsFeedData()
    }

    /**
     * progress bar visibility
     */
    private fun progressLytVisibility(isShow: Boolean) {
        if (isShow) {
            binding.lytProgress.visibility = View.VISIBLE
        } else {
            binding.lytProgress.visibility = View.GONE
        }
    }


    /**
     * get view model
     */
    override fun getViewModel(): HomeViewModel {
        return ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    /**
     * get toolbar
     */
    private fun getToolbar(): Toolbar? {
        return findViewById(R.id.toolbar_normal)
    }

    /**
     * observe live data from viewmodel
     */
    override fun startObservingLiveData() {
        super.startObservingLiveData()
        //init get news data
        getNewsFeedData()
        //News feed observer
        getViewModel()?.newsData?.observe(this, Observer {
            binding.swipeRefreshLayout?.apply {
                isRefreshing = false
            }
            progressLytVisibility(false)
            it.let { response ->
                getToolbar().let { it ->
                    txt_toolbar_title.text = response.title
                }
                renderListData(response.newsList)
            }
        })

    }

    /**
     * render news list
     */
    private fun renderListData(newsList: ArrayList<NewsFeedItem>) {

        newsList?.run {
            if (newsList.size > 0) {
                adapter = BaseAdapter(R.layout.item_news_list, this@MainActivity)
                adapter?.updateList(newsList)
                binding.recyclerNewsList.adapter = adapter
            }
        }
    }

    /**
     * list on click handling
     */
    override fun onClick(view: View?, data: NewsFeedItem?) {


    }
}
