package com.github.ratrofitwithmvc.controller

import android.content.Context
import com.github.ratrofitwithmvc.model.api.ApiClient
import com.github.ratrofitwithmvc.model.data.DataResponse
import com.github.ratrofitwithmvc.model.data.RowData
import com.github.ratrofitwithmvc.utils.CheckInternetConnectionAvailable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityController(listener: MainActivityListener) {

    var mainActivityListener: MainActivityListener

    init {
        mainActivityListener = listener
    }

    interface MainActivityListener {
        fun showProgress()
        fun hideProgress()
        fun onSuccess(data: ArrayList<RowData>)
        fun onFailure(message: String)
    }

    private var myCompositeDisposable: CompositeDisposable? = null

    fun getSearchUser(context: Context, query: String, page: Int) {
        // Check Internet connectivity
        if (context.CheckInternetConnectionAvailable()) {
            // API Calling Start
            mainActivityListener.showProgress()

            // Ratrofit API Calling
            myCompositeDisposable = CompositeDisposable()
            myCompositeDisposable?.add(
                ApiClient().getUserSearch(query, page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) })
            )
        } else {
            // Internet is not connected
            mainActivityListener.hideProgress()
            mainActivityListener.onFailure("Please check your internet connection!")
        }
    }

    private fun onResponse(response: DataResponse) {
        mainActivityListener.hideProgress()
        mainActivityListener.onSuccess(response.items!! as ArrayList<RowData>)
    }

    private fun onFailure(error: Throwable) {
        mainActivityListener.hideProgress()
        mainActivityListener.onFailure("Fail ${error.message}")
    }
}