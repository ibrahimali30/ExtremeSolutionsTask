package com.ibrahim.extremesolutionstask.marvel.presentation.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.ibrahim.extremesolutionstask.R
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Character
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.MarvelCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_marvel_character_details.*
import javax.inject.Inject

@AndroidEntryPoint
class MarvelCharacterDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel : MarvelCharactersViewModel

    lateinit var  charachter: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_marvel_character_details)

        charachter = intent?.getSerializableExtra(EXTRA_SERIZLIZABLE_OBJECT) as Character
        bindCharachterData(charachter)

    }

    private fun bindCharachterData(charachter: Character) {
        Glide.with(this)
            .load(charachter.thumbnail.getFullThumbnail())
            .into(ivTopImagePoster)
    }


    companion object{

        val EXTRA_SERIZLIZABLE_OBJECT = "character"
        fun startCallingIntent(
            character: Character,
            context: AppCompatActivity,
            viewToTransition: View
        ) {
            val activityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context, viewToTransition, "tr")
            val intent: Intent = Intent(
                context,
                MarvelCharacterDetailsActivity::class.java
            )
            intent.putExtra(EXTRA_SERIZLIZABLE_OBJECT, character)
            context.startActivity (intent, activityOptionsCompat.toBundle())
        }
    }
}