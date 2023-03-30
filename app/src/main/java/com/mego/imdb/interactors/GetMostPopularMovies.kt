package com.mego.imdb.interactors

import com.mego.imdb.data.MoviesRepository

class GetMostPopularMovies (private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(isOnline:Boolean=true) =
        moviesRepository.getMostPopularMovies(isOnline)

}