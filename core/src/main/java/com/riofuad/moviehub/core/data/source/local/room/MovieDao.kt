package com.riofuad.moviehub.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.riofuad.moviehub.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Update
    fun updateMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Query("SELECT * FROM movieList ")
    fun getListMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieList where bookmarked = 1")
    fun getBookmarkedMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieList WHERE movieId = :movieId")
    fun getDetailMovieById(movieId: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM movieList WHERE  title LIKE '%' || :search || '%'")
    fun searchMovies(search: String): Flow<List<MovieEntity>>
}