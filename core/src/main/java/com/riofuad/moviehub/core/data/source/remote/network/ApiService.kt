package com.riofuad.moviehub.core.data.source.remote.network

import com.riofuad.moviehub.core.data.source.remote.response.ListMovieResponse
import com.riofuad.moviehub.core.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.riofuad.moviehub.core.BuildConfig.MOVIE_TOKEN as ApiKey

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Query("api_key") apiKey: String = ApiKey
    ): ListMovieResponse<MovieResponse>

}