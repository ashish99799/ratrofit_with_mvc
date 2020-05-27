package com.github.ratrofitwithmvc.model.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ratrofitwithmvc.R
import com.github.ratrofitwithmvc.model.data.RowData
import com.github.ratrofitwithmvc.utils.LoadImage
import com.github.ratrofitwithmvc.utils.NewIntentWithData
import com.github.ratrofitwithmvc.view.activities.GithubUser
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main_cell.view.*

class GithubUserAdapter(
    private var context: Context,
    private val AdapterList: ArrayList<RowData>
) : RecyclerView.Adapter<GithubUserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_main_cell, parent, false)
        return ViewHolder(v)
    }

    fun AddAdapterList(list: ArrayList<RowData>) {
        for (element in list) {
            if (!AdapterList.contains(element)) {
                AdapterList.add(element)
            }
        }
//            this.AdapterList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return AdapterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        context.LoadImage(AdapterList[position].avatar_url!!, holder.imgAvatar)

        holder.lblUserName.text = AdapterList[position].login

        holder.itemView.setOnClickListener {
            val data = java.util.HashMap<String, Any>()
            data.put("GithubUser", Gson().toJson(AdapterList[position]).toString())

            (context as Activity).NewIntentWithData(
                GithubUser::class.java,
                data,
                false,
                false
            )
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var lblUserName = view.lblUserName
        var imgAvatar = view.imgAvatar
    }
}