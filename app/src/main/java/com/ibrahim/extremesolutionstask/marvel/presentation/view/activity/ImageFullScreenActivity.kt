package com.ibrahim.extremesolutionstask.marvel.presentation.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ibrahim.extremesolutionstask.R
import kotlinx.android.synthetic.main.activity_full_image_viewer.*


class ImageFullScreenActivity : AppCompatActivity() {

    private val gestureDetector: GestureDetector by lazy {
        GestureDetector(this, object : GestureDetector.OnGestureListener {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onShowPress(e: MotionEvent) {}
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return false
            }

            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                val builder = View.DragShadowBuilder(imageView)
                imageView.startDrag(null, builder, null, 0)
                Log.d("TAG", "onScroll: X: " + distanceX + "Y: " + distanceY)
                return true
            }

            override fun onLongPress(e: MotionEvent) {}
            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                return false
            }
        })
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image_viewer)



        val imageUrl = intent?.getStringExtra(EXTRA_IMAGE_URL)?:""
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        imageView.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    imageView.visibility = View.INVISIBLE
                }
                DragEvent.ACTION_DROP, DragEvent.ACTION_DRAG_ENDED -> {
                    onBackPressed()
                }
            }
            true
        }

        imageView.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event);
        }


    }


    companion object{

        val EXTRA_IMAGE_URL = "image"

        fun startCallingIntent(
            url: String,
            context: Activity,
            viewToTransition: View
        ) {
            val options = ActivityOptions.makeSceneTransitionAnimation(
                context,
                Pair(viewToTransition, "tr3")
            )

            val intent2 = Intent(context, ImageFullScreenActivity::class.java)
            intent2.putExtra(EXTRA_IMAGE_URL, url)

            context.startActivity(intent2, options.toBundle())

        }
    }


}
