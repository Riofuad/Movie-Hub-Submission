package com.riofuad.moviehub.core.domain.repository

import com.riofuad.moviehub.core.data.Resource
import com.riofuad.moviehub.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getAllMovie(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovie(): Flow<List<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)

    fun searchMovie(value: String): Flow<List<Movie>>
}