package com.riofuad.moviehub.favorite.di

import android.content.Context
import com.riofuad.moviehub.di.FavoriteModuleDependencies
import com.riofuad.moviehub.favorite.FavoriteFragment
import dagger.BindsInstance
import dagger.Component


@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteModule {
    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(FavoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteModule
    }
}