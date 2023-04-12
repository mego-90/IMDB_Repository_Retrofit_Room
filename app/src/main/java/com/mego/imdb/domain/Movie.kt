package com.mego.imdb.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Movie(
    @PrimaryKey
    var id : String,
    var year : Int?,
    var title : String,
    var image : Poster
) : Serializable {
    override fun toString(): String {
        return title
    }
}