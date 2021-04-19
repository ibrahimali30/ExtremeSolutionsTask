package com.ibrahim.extremesolutionstask.marvel.presentation.view.extensions

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.widget.SearchView
import com.ibrahim.extremesolutionstask.R
import com.ibrahim.extremesolutionstask.marvel.presentation.view.activity.MainActivity
import com.ibrahim.extremesolutionstask.marvel.presentation.view.fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

 fun MainActivity.openSearch() {
    searchView.setQuery("" , false)
    cardView.visibility = View.VISIBLE
    blurView.visibility = View.VISIBLE
    toolBar.visibility = View.INVISIBLE
    searchView.isActivated = true
    searchView.setIconifiedByDefault(false)
    searchView.isIconified = false

    val circularReveal = ViewAnimationUtils.createCircularReveal(
        cardView,
        (open_search_button.right + open_search_button.left) / 2,
        (open_search_button.top + open_search_button.bottom) / 2,
        0f, searchView.width.toFloat()
    )
    circularReveal.duration = 500
    circularReveal.start()

    supportFragmentManager.beginTransaction()
        .replace(R.id.flSearchResult , SearchFragment())
        .addToBackStack("")
        .commit()
}


fun SearchView.setOnTextChanged(function: (quertText:String) -> Unit) {

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            function(newText)
            return false
        }
    })
}


 fun MainActivity.closeSearch() {
    supportFragmentManager.popBackStack()
    blurView.visibility = View.INVISIBLE

    val circularConceal = ViewAnimationUtils.createCircularReveal(
        cardView,
        (open_search_button.right + open_search_button.left) / 2,
        (open_search_button.top + open_search_button.bottom) / 2,
        searchView.width.toFloat(), 0f
    )

    circularConceal.duration = 500
    circularConceal.start()
    circularConceal.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) = Unit
        override fun onAnimationCancel(animation: Animator?) = Unit
        override fun onAnimationStart(animation: Animator?) = Unit
        override fun onAnimationEnd(animation: Animator?) {
            cardView.visibility = View.INVISIBLE
            toolBar.visibility = View.VISIBLE
            circularConceal.removeAllListeners()
        }
    })
}