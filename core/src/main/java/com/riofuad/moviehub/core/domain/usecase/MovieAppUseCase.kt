package com.riofuad.moviehub.core.domain.usecase

import com.riofuad.moviehub.core.data.Resource
import com.riofuad.moviehub.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieAppUseCase {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
    fun searchMovie(value: String): Flow<List<Movie>>
}