package com.riofuad.moviehub.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.riofuad.moviehub.core.domain.usecase.MovieAppUseCase

class FavoriteViewModel(movieAppUseCase: MovieAppUseCase) : ViewModel() {
    val moviesFavorite = movieAppUseCase.getFavoriteMovie().asLiveData()
}