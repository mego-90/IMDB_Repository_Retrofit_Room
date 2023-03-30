package com.mego.imdb.framework.retrofit

import com.mego.imdb.data.MoviesDataSource
import com.mego.imdb.domain.Movie
import kotlinx.coroutines.flow.*

class RetrofitMoviesDataSource (private val moviesRemoteService: MoviesRemoteService) :
    MoviesDataSource {

    override suspend fun movieTitleAutocomplete(input: String): Flow<List<Movie>> {
        return flow { emit ( moviesRemoteService.movieTitleAutocomplete(input)
                        .suggestedMovies
                        .map { Movie(it.id, it.year, it.title, it.poster) }
        ) }
    }

    override suspend fun getDetailsOfMovie(id: String): Movie? {
        return moviesRemoteService.getDetailsOfMovie(id)
    }

    suspend fun getMostPopularMoviesIDs(): Flow<List<String>> {
        return flow { emit(moviesRemoteService.getMostPopularMovies()) }
    }

    companion object {
        fun extractMovieID(path: String) =
            path.split("/")[2]
    }
}