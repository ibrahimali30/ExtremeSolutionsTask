package com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ibrahim.extremesolutionstask.R
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Character
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Thumbnail
import kotlinx.android.synthetic.main.item_marvel_character_discovery.view.*
import kotlin.collections.ArrayList

class HomeCharacterAdapter(
    val data: ArrayList<Character>,
    val function: (size: Int) -> Unit = {},
    val onItemClicked: (character: Character, viewToTransition: View, viewToTransition2: View) -> Unit
) : RecyclerView.Adapter<HomeCharacterAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_marvel_character_discovery, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        if (position > data.size - 4) {
            function(data.size)
        }

        holder.itemView.setOnClickListener {
            onItemClicked(data[position] , holder.view.ivPoster, holder.view.tvItemTitle)
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

    class ViewHolder constructor(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(model: Character) {
            bindThmbnail(model.thumbnail)
            view.tvItemTitle.text = model.getCharacterTitle()
        }

        private fun bindThmbnail(thumbnail: Thumbnail?) {
            thumbnail ?: return
            val requestOptions = RequestOptions()
            val glideApp = Glide.with(view)
                .load(thumbnail.path + "." + thumbnail.extension)

            glideApp.apply(requestOptions)
                .apply(RequestOptions().transform(RoundedCorners(10)))
                .placeholder(R.drawable.bg_search_radius)
                .into(view.ivPoster)
        }

    }
}