package com.mego.imdb.framework.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mego.imdb.domain.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDAO {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(vararg movie: Movie)

    @Query ("SELECT * FROM Movie WHERE id = :id")
    fun getMovieById (id:String) : Movie?

    @Query("SELECT * FROM Movie ORDER BY RANDOM() LIMIT 8")
    fun getRandom8Movies() :Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE title LIKE :input || '%' LIMIT 5")
    fun moviesTitleAutocomplete(input:String) : Flow<List<Movie>>

}