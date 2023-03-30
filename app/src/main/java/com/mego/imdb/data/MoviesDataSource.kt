package com.mego.imdb.data

import com.mego.imdb.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesDataSource {

    suspend fun movieTitleAutocomplete( input:String ) : Flow<List<Movie>>

    suspend fun getDetailsOfMovie( id :String ) : Movie?

//    suspend fun getMostPopularMoviesIDs() : Flow<List<String>>

}