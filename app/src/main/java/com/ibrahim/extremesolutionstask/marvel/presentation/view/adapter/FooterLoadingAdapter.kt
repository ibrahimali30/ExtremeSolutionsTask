package com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.extremesolutionstask.R

class FooterLoadingAdapter : RecyclerView.Adapter<FooterWishListViewHolder>() {
    private var footerItemSize = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterWishListViewHolder {
        return parent.let { LayoutInflater.from(it.context) }
            .inflate(R.layout.item_list_loading, parent, false)
            .let { FooterWishListViewHolder(it) }
    }

    override fun getItemCount() = footerItemSize
    override fun onBindViewHolder(holder: FooterWishListViewHolder, position: Int) {}

    fun showLoading() {
        footerItemSize = 1
        notifyDataSetChanged()
    }

    fun hideLoading() {
        footerItemSize = 0
        notifyDataSetChanged()
    }

    fun setLoading(show: Boolean) {
        if (show)
            showLoading()
        else
            hideLoading()
    }
}

class FooterWishListViewHolder(view: View) : RecyclerView.ViewHolder(view)
