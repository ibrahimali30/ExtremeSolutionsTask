package com.ibrahim.extremesolutionstask.marvel.presentation.view.extensions

import androidx.appcompat.widget.SearchView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily


fun ShapeableImageView.setCornerRadius() {
    shapeAppearanceModel =
        this.shapeAppearanceModel
            .toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED,40f)
            .setBottomRightCorner(CornerFamily.ROUNDED,40f)
            .build();
}




fun SearchView.setOnTextChanged(function: (queryText:String) -> Unit) {

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            function(newText?:"")
            return false
        }
    })
}