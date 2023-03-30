package com.mego.imdb.framework.retrofit

import com.mego.imdb.framework.retrofit.responses.ResponseAutocomplete
import com.mego.imdb.domain.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesRemoteService {

    @GET("auto-complete")
    suspend fun movieTitleAutocomplete(@Query("q") input: String): ResponseAutocomplete

    @GET("title/get-details")
    suspend fun getDetailsOfMovie(@Query("tconst") id: String): Movie?
    
    @GET("title/get-most-popular-movies")
    suspend fun getMostPopularMovies(): List<String>

}