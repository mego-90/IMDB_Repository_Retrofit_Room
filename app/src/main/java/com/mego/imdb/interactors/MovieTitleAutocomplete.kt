package com.mego.imdb.interactors

import com.mego.imdb.data.MoviesRepository
import com.mego.imdb.domain.Movie

class MovieTitleAutocomplete ( private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(input : String,isOnline:Boolean=true) =
         moviesRepository.movieTitleAutocomplete(input,isOnline)

}