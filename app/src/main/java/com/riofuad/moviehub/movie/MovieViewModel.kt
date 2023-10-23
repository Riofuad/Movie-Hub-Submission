package com.riofuad.moviehub.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.riofuad.moviehub.core.domain.usecase.MovieAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@HiltViewModel
class MovieViewModel @Inject constructor(movieAppUseCase: MovieAppUseCase) : ViewModel() {
    val movies = movieAppUseCase.getAllMovie().asLiveData()

    private val queryChannel = ConflatedBroadcastChannel<String>()

    fun setSearchQuery(search: String) {
        queryChannel.trySend(search)
    }

        val movieResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieAppUseCase.searchMovie(it)
        }.asLiveData()
//    val movieResult = channelFlow {
//        queryChannel.asFlow()
//            .debounce(300)
//            .distinctUntilChanged()
//            .filter {
//                it.trim().isNotEmpty()
//            }
//            .collect {
//                val result = movieAppUseCase.searchMovie(it)
//                send(result)
//            }
//    }.asLiveData()
}