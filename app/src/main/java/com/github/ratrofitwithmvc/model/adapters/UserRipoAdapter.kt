package com.github.ratrofitwithmvc.model.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.github.ratrofitwithmvc.R
import com.github.ratrofitwithmvc.model.data.UserRipoData
import kotlinx.android.synthetic.main.github_user_cell.view.*
import java.util.*
import kotlin.collections.ArrayList

class UserRipoAdapter(
    private var context: Context,
    private val AdapterList: ArrayList<UserRipoData>
) : RecyclerView.Adapter<UserRipoAdapter.ViewHolder>(), Filterable {

    var FilterList = ArrayList<UserRipoData>()

    init {
        FilterList = AdapterList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.github_user_cell, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return FilterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lblRipoName.text = FilterList[position].name
        holder.lblForks.text = "" + FilterList[position].forks + " Forks"
        holder.lblStars.text = "" + FilterList[position].stargazers_count + " Stars"
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var lblRipoName = view.lblRipoName
        var lblForks = view.lblForks
        var lblStars = view.lblStars
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    FilterList = AdapterList
                } else {
                    val resultList = ArrayList<UserRipoData>()
                    for (row in AdapterList) {
                        if (row.name!!.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    FilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = FilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                FilterList = results?.values as ArrayList<UserRipoData>
                notifyDataSetChanged()
            }
        }
    }
}