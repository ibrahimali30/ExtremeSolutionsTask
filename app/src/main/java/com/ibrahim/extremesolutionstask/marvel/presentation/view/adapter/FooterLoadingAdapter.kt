package com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.extremesolutionstask.R
import kotlinx.android.synthetic.main.item_list_loading.view.*
import kotlinx.android.synthetic.main.layout_error_view.view.*


class FooterLoadingAdapter : RecyclerView.Adapter<FooterWishListViewHolder>() {
    private var footerItemSize = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterWishListViewHolder {
        return parent.let { LayoutInflater.from(it.context) }
            .inflate(R.layout.item_list_loading, parent, false)
            .let { FooterWishListViewHolder(it) }
    }

    override fun getItemCount() = footerItemSize

    var holder: FooterWishListViewHolder? = null
    override fun onBindViewHolder(holder: FooterWishListViewHolder, position: Int) {
        this.holder = holder
    }





    fun setLoading(show: Boolean = true) {
        if (show)
            holder?.showLoading()
        else
            holder?.hideLoading()
    }

    fun showError(retryFunction: () -> Unit) {
        holder?.showError(retryFunction)
    }
}

class FooterWishListViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    fun showLoading() {
        view.errorViewLayout.visibility = View.INVISIBLE
        view.progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        view.errorViewLayout.visibility = View.INVISIBLE
        view.progressBar.visibility = View.INVISIBLE
    }

    fun showError(retryFunction: () -> Unit) {
        view.progressBar.visibility = View.INVISIBLE
        view.errorViewLayout.visibility = View.VISIBLE
        view.errorViewLayout.btRetry.setOnClickListener {
            retryFunction()
        }
    }
}
