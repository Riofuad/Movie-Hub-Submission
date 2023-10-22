package com.riofuad.moviehub.di

import com.riofuad.moviehub.core.domain.usecase.MovieAppAppInteractor
import com.riofuad.moviehub.core.domain.usecase.MovieAppUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideMovieRepository(movieAppInteractor: MovieAppAppInteractor): MovieAppUseCase
}