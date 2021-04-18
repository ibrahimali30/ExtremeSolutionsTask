package com.ibrahim.extremesolutionstask.marvel.presentation.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.ibrahim.extremesolutionstask.R
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Character
import com.ibrahim.extremesolutionstask.marvel.presentation.view.fragment.CharacterSubCategoryFragment
import com.ibrahim.extremesolutionstask.marvel.presentation.view.fragment.SearchFragment
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
            .load(charachter.thumbnail?.getFullThumbnail())
            .into(ivTopImagePoster)



        charachter.comics.let {
            addListFragment("comics", charachter.id.toString() , 1)
        }

        charachter.events.let {
            addListFragment("events", charachter.id.toString() , 2)
        }

        charachter.series.let {
            addListFragment("series", charachter.id.toString() , 3)
        }

        charachter.stories.let {
            addListFragment("stories", charachter.id.toString() , 4)
        }

    }


    private fun addListFragment(name: String, id: String, tag: Int) {
        val containerFrameLayout = FrameLayout(this)
        containerFrameLayout.id = tag + 1
        containerFrameLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        llVodRowFragmentContainers.addView(containerFrameLayout)

        val fragment = CharacterSubCategoryFragment.newInstance(id, name)

        supportFragmentManager.beginTransaction()
            .replace(containerFrameLayout.id , fragment)
            .commit()
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