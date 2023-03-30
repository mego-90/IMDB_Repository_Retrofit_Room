package com.mego.imdb.domain

import androidx.room.TypeConverter

class PosterTypeConverter {

    @TypeConverter
    fun toPoster(url:String) : Poster =
        Poster(url)

    @TypeConverter
    fun posterToUrl(poster: Poster) : String =
        poster.url

}