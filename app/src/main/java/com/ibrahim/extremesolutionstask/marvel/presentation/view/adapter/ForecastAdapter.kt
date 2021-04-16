package com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ibrahim.extremesolutionstask.R
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Result
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Thumbnail
import com.ibrahim.extremesolutionstask.marvel.presentation.view.activity.MainActivity
import kotlinx.android.synthetic.main.item_forecast.view.*
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ForecastAdapter(val data: ArrayList<Result>) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setList(list: List<Result>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: Result) {
            bindThmbnail(model.thumbnail)

        }

        private fun bindThmbnail(thumbnail: Thumbnail) {
            val requestOptions = RequestOptions()
            val glideApp = Glide.with(itemView)
                .load(thumbnail.path + "." + thumbnail.extension)

            glideApp.apply(requestOptions)
                .transform(object : BitmapTransformation() {
                    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

                    }

                    override fun transform(pool: BitmapPool,
                                           toTransform: Bitmap, outWidth: Int,
                                           outHeight: Int): Bitmap {
                        return Bitmap.createBitmap(toTransform, 0, 0,
                            toTransform.getWidth(), toTransform.getWidth());
                    }



                })
                .apply(RequestOptions().transform(RoundedCorners(10)))
                .into(itemView.ivPoster)
        }


        val v = object : BitmapTransformation() {
            override fun updateDiskCacheKey(messageDigest: MessageDigest) {

            }

            override fun transform(pool: BitmapPool,
                                   toTransform: Bitmap, outWidth: Int,
                                   outHeight: Int): Bitmap {
                return Bitmap.createBitmap(toTransform, 0, 0,
                    toTransform.getWidth(), toTransform.getWidth());
            }



        }
    }
}