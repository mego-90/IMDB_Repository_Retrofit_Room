package com.mego.imdb.interactors

import com.mego.imdb.data.MoviesRepository

class GetDetailsOfMovie (private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke (id : String ) =
        moviesRepository.getDetailsOfMovie(id)

}