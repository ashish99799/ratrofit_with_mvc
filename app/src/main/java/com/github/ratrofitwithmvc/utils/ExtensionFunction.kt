package com.github.ratrofitwithmvc.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.ratrofitwithmvc.R
import java.util.HashMap

val INTENT_DATA = "INTENT_DATA"

/**
 * Check internet connection.
 *
 * @return  boolean value for internet connection.
 */
fun Context.CheckInternetConnectionAvailable(): Boolean {
    if (CheckInternetConnection()) {
        Log.d("Network", "Connected")
        return true
    } else {
        CheckNetworkConnectionDialog(
            resources.getString(R.string.no_connection),
            resources.getString(R.string.turn_on_connection)
        )
        Log.d("Network", "Not Connected")
        return false
    }
}

/**
 * Check internet connection.
 *
 * @return  boolean value for internet connection.
 */
fun Context.CheckInternetConnection(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    val isConnected = activeNetwork != null && activeNetwork.isConnected
    if (isConnected) {
        return true
    } else {
        return false
    }
}

/**
 * To display alert dialog when no network available.
 *
 * @param title display title for alert dialog.
 * @param msg display msg for alert dialog.
 */
fun Context.CheckNetworkConnectionDialog(title: String, msg: String) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(msg)
    builder.setNegativeButton(getString(R.string.ok)) { dialog, which -> dialog.dismiss() }
    val alertDialog = builder.create()
    alertDialog.show()
}

/**
 * To display toast in application
 *
 * @param  msg display msg for toast.
 */
fun Context.ToastMessage(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
}

/**
 * Load Image into
 * @ImageView
 * using Glide
 * */
fun Context.LoadImage(imgUrl: String, img: ImageView) {
    Glide.with(this)
        .load(imgUrl)
        .placeholder(R.drawable.ic_loading_image)
        .error(R.drawable.ic_no_image)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .centerCrop()
        .into(img)
}


fun Activity.NewIntentWithData(
    ourClass: Class<*>,
    hashMap: HashMap<String, Any>,
    isAnimation: Boolean,
    isFinish: Boolean
) {
    val intent = Intent(this, ourClass)
    intent.putExtra(INTENT_DATA, hashMap)
    startActivity(intent)
    if (isAnimation) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    } else {
        overridePendingTransition(R.anim.animation_one, R.anim.animation_two)
    }
    if (isFinish) {
        finish()
    }
}

