package com.mego.imdb.data

import android.content.Context
import com.mego.imdb.framework.retrofit.RetrofitMoviesDataSource
import com.mego.imdb.domain.Movie
import com.mego.imdb.framework.room.RoomMoviesDataSource
import kotlinx.coroutines.flow.*
import java.net.UnknownHostException

class MoviesRepository (
    private val retrofitMoviesDataSource: RetrofitMoviesDataSource,
    private val roomMoviesDataSource: RoomMoviesDataSource ) {

    suspend fun movieTitleAutocomplete( input:String , isOnline:Boolean) : Flow<List<Movie>> {
        return if (isOnline)
            retrofitMoviesDataSource.movieTitleAutocomplete(input)
        else
            roomMoviesDataSource.movieTitleAutocomplete(input)
    }


    suspend fun getDetailsOfMovie( id :String ) : Movie? {

        try {
            return roomMoviesDataSource.getDetailsOfMovie(id) ?:
                retrofitMoviesDataSource.getDetailsOfMovie(id)
        } catch (_: UnknownHostException) { return null}
    }

    suspend fun getMostPopularMovies(isOnline:Boolean) : Flow<Movie> {

        val retrofitFlow = retrofitMoviesDataSource
            .getMostPopularMoviesIDs()
            .flatMapConcat { it.shuffled().asFlow() }
            .take(2)
            .mapNotNull {
                roomMoviesDataSource.getDetailsOfMovie(extractMovieID(it))
                    ?: retrofitMoviesDataSource.getDetailsOfMovie(extractMovieID(it))
            }.onEach {
                roomMoviesDataSource.insertMovies(it)
            }

        val roomFlow = roomMoviesDataSource
            .getMostPopularMovies()
            .flatMapConcat { it.asFlow() }

        return if (isOnline) retrofitFlow else roomFlow

    }

    private fun extractMovieID(url: String) =
        RetrofitMoviesDataSource.extractMovieID(url)

}