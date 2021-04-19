package com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ibrahim.extremesolutionstask.R
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Character
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Thumbnail
import kotlinx.android.synthetic.main.item_marvel_character_search.view.*


class SearchAdapter(val data: ArrayList<Character>, val function: (size:Int) -> Unit = {}) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var searchQuery = ""

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_marvel_character_search, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], searchQuery)
        if (position > data.size - 4){
            function(data.size)
        }
    }

    fun setList(list: List<Character>) {
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: Character, searchQuery: String) {
            bindThumbnail(model.thumbnail)
            bindName(model.getCharacterTitle() , searchQuery)

        }

        private fun bindName(name: String, searchQuery: String) {
            var index = -1
            val spannable = SpannableString(name)
            while (name.toLowerCase().indexOf(searchQuery.toLowerCase(), index + 1).also { index = it } >= 0) {
                highlightWord(spannable , index, searchQuery.length)
            }
            itemView.tvItemTitle.text = spannable
        }

        private fun highlightWord(spannable: Spannable, index: Int, length: Int) {
            spannable.setSpan(BackgroundColorSpan(Color.RED), index, index + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        private fun bindThumbnail(thumbnail: Thumbnail?) {
            thumbnail ?: return
            val requestOptions = RequestOptions()
            val glideApp = Glide.with(itemView)
                .load(thumbnail.getFullThumbnail())

            glideApp.apply(requestOptions)
                    .apply(RequestOptions().transform(RoundedCorners(10)))
                    .placeholder(R.drawable.bg_search_radius)
                    .into(itemView.ivPoster)
        }

    }
}