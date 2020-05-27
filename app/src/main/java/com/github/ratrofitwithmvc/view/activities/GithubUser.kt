package com.github.ratrofitwithmvc.view.activities

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.ratrofitwithmvc.R
import com.github.ratrofitwithmvc.model.adapters.UserRipoAdapter
import com.github.ratrofitwithmvc.model.data.RowData
import com.github.ratrofitwithmvc.model.data.UserData
import com.github.ratrofitwithmvc.model.data.UserRipoData
import com.github.ratrofitwithmvc.utils.INTENT_DATA
import com.github.ratrofitwithmvc.utils.LoadImage
import com.github.ratrofitwithmvc.utils.ToastMessage
import com.github.ratrofitwithmvp.controller.GithubUserController
import com.google.gson.Gson
import kotlinx.android.synthetic.main.github_user.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GithubUser : AppCompatActivity(),
    GithubUserController.GithubUserListener,
    SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    // Variable Declaration
    var context: Context? = null

    lateinit var hashMap: HashMap<String, Any>
    lateinit var rowData: RowData

    lateinit var ripoList: ArrayList<UserRipoData>

    var controller: GithubUserController? = null
    var userRipoAdapter: UserRipoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.github_user)

        context = this

        hashMap = intent.getSerializableExtra(INTENT_DATA) as (java.util.HashMap<String, Any>)
        rowData = Gson().fromJson(hashMap.get("GithubUser").toString(), RowData::class.java)

        // Setup SupportActionBar to over Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = rowData.login

        initView()
    }

    fun initView() {
        LoadImage(rowData.avatar_url!!, imgAvatar)
        lblUserName.text = rowData.login

        // Setup Controller
        controller = GithubUserController(this)

        rvUserRipo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // setup SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        swipeRefreshLayout.isRefreshing = true

        // Call Api User Info
        controller!!.onUserInfo(context!!, rowData.login!!)

        ripoList = ArrayList<UserRipoData>()
        onRefresh()

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
        ripoList = ArrayList<UserRipoData>()
        // Call Api User Ripo
        controller!!.onUserRipo(context!!, rowData.login!!)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        userRipoAdapter!!.filter.filter(newText)
        return false
    }

    override fun showProgress() {
        // Api Calling started
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        // Api Calling ended
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onSuccess(data: UserData) {
        // Success API Response
        lblUserName.text = data.login ?: ""
        lblEmail.text = "Email : " + data.email ?: ""
        lblLocation.text = "Location : " + data.location ?: ""
        lblFollowers.text = "" + data.followers + " Followers"
        lblFollowing.text = "Following " + data.following

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("dd-MM-yyyy")

        val my_date = data.created_at
        val date: Date = inputFormat.parse(my_date)

        lblJoinDate.text = "Join Date : " + outputFormat.format(date)
    }

    override fun onSuccess(data: List<UserRipoData>) {
        // Success API Response
        if (ripoList.isEmpty()) {
            ripoList.addAll(data)
        }

        userRipoAdapter = UserRipoAdapter(context!!, ripoList)
        rvUserRipo.adapter = userRipoAdapter
    }

    override fun onFailure(message: String) {
        // Error & Failure
        swipeRefreshLayout.isRefreshing = false
        ToastMessage(message)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
