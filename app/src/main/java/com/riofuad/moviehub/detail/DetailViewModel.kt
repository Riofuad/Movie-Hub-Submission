package com.riofuad.moviehub.detail

import androidx.lifecycle.ViewModel
import com.riofuad.moviehub.core.domain.model.Movie
import com.riofuad.moviehub.core.domain.usecase.MovieAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieAppUseCase: MovieAppUseCase) :
    ViewModel() {
    fun setFavoriteMovie(movie: Movie, newState: Boolean) =
        movieAppUseCase.setFavoriteMovie(movie, newState)
}