package com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CharactersSharedViewModel(): ViewModel() {

    var searchQueryLiveData = MutableLiveData<String>()
}