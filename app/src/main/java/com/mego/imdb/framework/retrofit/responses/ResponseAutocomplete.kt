package com.mego.imdb.framework.retrofit.responses

import com.google.gson.annotations.SerializedName
import com.mego.imdb.domain.Poster


class ResponseAutocomplete {

    @SerializedName("d")
    val suggestedMovies = ArrayList<moviesAutocomplete>()

    inner class moviesAutocomplete() {
        lateinit var id : String
        @SerializedName("l")
        lateinit var title:String
        @SerializedName("i")
        lateinit var poster : Poster
        @SerializedName("y")
        val year : Int? = null
    }
}