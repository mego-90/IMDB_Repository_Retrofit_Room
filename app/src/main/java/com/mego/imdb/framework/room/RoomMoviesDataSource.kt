package com.mego.imdb.framework.room

import com.mego.imdb.data.MoviesDataSource
import com.mego.imdb.domain.Movie
import kotlinx.coroutines.flow.Flow

class RoomMoviesDataSource(private val moviesDAO: MoviesDAO) : MoviesDataSource {

    override suspend fun movieTitleAutocomplete(input: String): Flow<List<Movie>> {
        return moviesDAO.moviesTitleAutocomplete(input)
    }

    override suspend fun getDetailsOfMovie(id: String): Movie? {
        return moviesDAO.getMovieById(id)
    }

    fun getMostPopularMovies(): Flow<List<Movie>> {
        return moviesDAO.getRandom8Movies()
    }

    suspend fun insertMovies( vararg movie: Movie) {
        movie.onEach {
            moviesDAO.insertMovies(it)
        }

    }
}