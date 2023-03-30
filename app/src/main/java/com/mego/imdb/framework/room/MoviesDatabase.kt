package com.mego.imdb.framework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mego.imdb.domain.Movie
import com.mego.imdb.domain.PosterTypeConverter

@Database(entities = [Movie::class], version = 6 )
@TypeConverters(PosterTypeConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val moviesDAO : MoviesDAO

    companion object {
        const val name = "movies_db"
    }

}