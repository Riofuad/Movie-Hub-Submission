package com.riofuad.moviehub.core.domain.usecase

import com.riofuad.moviehub.core.data.Resource
import com.riofuad.moviehub.core.domain.model.Movie
import com.riofuad.moviehub.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieAppAppInteractor @Inject constructor(private val movieRepository: IMovieRepository) :
    MovieAppUseCase {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> = movieRepository.getAllMovie()
    override fun getFavoriteMovie(): Flow<List<Movie>> = movieRepository.getFavoriteMovie()
    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)

    override fun searchMovie(value: String): Flow<List<Movie>> = movieRepository.searchMovie(value)

}