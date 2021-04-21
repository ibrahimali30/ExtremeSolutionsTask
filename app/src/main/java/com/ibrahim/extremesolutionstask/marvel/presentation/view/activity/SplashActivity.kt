package com.ibrahim.extremesolutionstask.marvel.presentation.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibrahim.extremesolutionstask.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_splash)

        Glide.with(this)
            .load(R.drawable.mcu_background)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(10, 6)))
            .into(imageView)


        Handler().postDelayed({
            startActivity(Intent(this ,  HomeActivity::class.java))
            finish()
        },1000L)
    }
}