package com.mego.imdb.domain

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.JsonAdapter

@Entity
data class Movie(
    @PrimaryKey
    var id : String,
    var year : Int?,
    var title : String,
    var image : Poster
) {
    override fun toString(): String {
        return title
    }
}