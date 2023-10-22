package com.riofuad.moviehub.di

import com.riofuad.moviehub.core.domain.usecase.MovieAppUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun movieUseCase(): MovieAppUseCase
}