package com.github.ratrofitwithmvp.controller

import android.content.Context
import com.github.ratrofitwithmvc.model.api.ApiClient
import com.github.ratrofitwithmvc.model.data.UserData
import com.github.ratrofitwithmvc.model.data.UserRipoData
import com.github.ratrofitwithmvc.utils.CheckInternetConnectionAvailable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GithubUserController(listener: GithubUserListener) {

    var githubUserListener: GithubUserListener

    init {
        githubUserListener = listener
    }

    interface GithubUserListener {
        fun showProgress()
        fun hideProgress()
        fun onSuccess(data: List<UserRipoData>)
        fun onSuccess(data: UserData)
        fun onFailure(message: String)
    }

    // Over Activity Listener
    private var myCompositeDisposable: CompositeDisposable? = null

    fun onUserInfo(context: Context, query: String) {
        // Check Internet connectivity
        if (context.CheckInternetConnectionAvailable()) {
            // Ratrofit API Calling
            myCompositeDisposable = CompositeDisposable()
            myCompositeDisposable?.add(
                ApiClient()
                    .getUserInfo(query)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) })
            )
        } else {
            // Internet is not connected
            githubUserListener.onFailure("Please check your internet connection!")
        }
    }

    private fun onResponse(response: UserData) {
        githubUserListener.hideProgress()
        githubUserListener.onSuccess(response)
    }

    private fun onResponseList(response: List<UserRipoData>) {
        githubUserListener.hideProgress()
        githubUserListener.onSuccess(response)
    }

    private fun onFailure(error: Throwable) {
        githubUserListener.hideProgress()
        githubUserListener.onFailure("Fail ${error.message}")
    }

    fun onUserRipo(context: Context, query: String) {
        // Check Internet connectivity
        if (context.CheckInternetConnectionAvailable()) {
            // API Calling Start
            githubUserListener.showProgress()

            // Ratrofit API Calling
            myCompositeDisposable = CompositeDisposable()
            myCompositeDisposable?.add(
                ApiClient()
                    .getUserRipo(query)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response -> onResponseList(response) }, { t -> onFailure(t) })
            )
        } else {
            // Internet is not connected
            githubUserListener.hideProgress()
            githubUserListener.onFailure("Please check your internet connection!")
        }
    }
}