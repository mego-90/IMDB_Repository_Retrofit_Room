package com.mego.imdb.presentation.autocomplete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mego.imdb.domain.Movie
import com.mego.imdb.interactors.MovieTitleAutocomplete
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AutocompleteViewModel(private val moviesTitleAutocomplete: MovieTitleAutocomplete) : ViewModel() {

    private val _moviesAutocompleteMutableLiveData = MutableLiveData<ArrayList<Movie>>()
    val moviesAutocompleteLiveData : LiveData<ArrayList<Movie>>
        get() = _moviesAutocompleteMutableLiveData

    fun updateAutocompleteList(input:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                moviesTitleAutocomplete(input)
                    .collect {
                        _moviesAutocompleteMutableLiveData.postValue(ArrayList(it))
                    }
            } catch (ex:Exception) {
                moviesTitleAutocomplete(input,isOnline = false)
                    .collect {
                        _moviesAutocompleteMutableLiveData.postValue(ArrayList(it))
                    }
            }
        }
    }
}