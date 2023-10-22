package com.riofuad.moviehub.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riofuad.moviehub.core.domain.usecase.MovieAppUseCase
import com.riofuad.moviehub.favorite.FavoriteViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val movieAppUseCase: MovieAppUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(movieAppUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}