package com.mego.imdb.presentation.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mego.imdb.domain.Movie
import com.mego.imdb.interactors.GetMostPopularMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PopularMoviesViewModel (private val getMostPopularMovies : GetMostPopularMovies): ViewModel() {

    private val popularMoviesList = ArrayList<Movie>()

    private val _popularMoviesMutableLiveData = MutableLiveData( popularMoviesList)
    val popularMoviesLiveData : LiveData<ArrayList<Movie>>
        get() = _popularMoviesMutableLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>(true)
    val isLoadingLiveData : LiveData<Boolean>
        get() = _isLoadingLiveData

    private fun updateMostPopularMovies() {
        _isLoadingLiveData.value = true

        viewModelScope.launch(Dispatchers.IO ) {
            try {
                getMostPopularMovies()
                    .collect { updateLiveData(it) }
            } catch (ex:Exception) {
                getMostPopularMovies( isOnline = false )
                    .collect { updateLiveData(it) }
            }
        }
    }

    init {
        updateMostPopularMovies()
    }

    private fun updateLiveData(movie:Movie) {
        popularMoviesList.add(movie)
        _popularMoviesMutableLiveData.postValue( popularMoviesList)
        if (_isLoadingLiveData.value == true)
            _isLoadingLiveData.postValue(false)
    }

}