package com.github.ratrofitwithmvc.view.activities

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.ratrofitwithmvc.R
import com.github.ratrofitwithmvc.controller.MainActivityController
import com.github.ratrofitwithmvc.model.adapters.GithubUserAdapter
import com.github.ratrofitwithmvc.model.data.RowData
import com.github.ratrofitwithmvc.utils.ToastMessage
import com.github.ratrofitwithmvc.wegates.RefreshLayoutHelper
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    MainActivityController.MainActivityListener,
    OnLoadmoreListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    // Variable Declaration
    var context: Context? = null
    var query: String = ""
    var page: Int = 1
    lateinit var listProducts: ArrayList<RowData>

    var controller: MainActivityController? = null
    var githubUserAdapter: GithubUserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup SupportActionBar to over Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)

        context = this

        // Init View
        initView()
    }

    fun initView() {
        // Setup Controller
        controller = MainActivityController(this)

        rvGithubUsers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        smartRefresh.isEnableRefresh = false
        smartRefresh.isEnableOverScrollBounce = true
        RefreshLayoutHelper.initToLoadMoreStyle(smartRefresh, this)

        // setup SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)

        searchView.setOnQueryTextListener(this)
        val searchClose =
            searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView
        searchClose.setOnClickListener {
            searchView.clearFocus()
            searchView.setQuery("", false)
            onRefresh()
        }
    }

    override fun onRefresh() {
        query = ""
        page = 1
        listProducts = ArrayList<RowData>()
        githubUserAdapter = GithubUserAdapter(context!!, listProducts)
        rvGithubUsers.adapter = githubUserAdapter
        hideProgress()
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        // Call Api second page
        page++
        controller!!.getSearchUser(context!!, query, page)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (TextUtils.isEmpty(newText)) {
            return false
        }

        query = newText.toString()
        page = 1
        listProducts = ArrayList<RowData>()
        controller!!.getSearchUser(context!!, query, page)
        return false
    }

    override fun showProgress() {
        // Api Calling started
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        // Api Calling ended
        swipeRefreshLayout.isRefreshing = false
        if (smartRefresh.isLoading) {
            smartRefresh.finishLoadmore()
        }
    }

    override fun onSuccess(data: ArrayList<RowData>) {
        // Success API Response
        if (listProducts.isEmpty()) {
            listProducts.addAll(data)
            githubUserAdapter = GithubUserAdapter(context!!, listProducts)
            rvGithubUsers.adapter = githubUserAdapter
        } else {
            githubUserAdapter!!.AddAdapterList(data)
        }
    }

    override fun onFailure(message: String) {
        // Error & Failure
        ToastMessage(message)
    }

}
