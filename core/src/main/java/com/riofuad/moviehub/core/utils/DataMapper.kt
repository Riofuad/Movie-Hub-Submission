package com.riofuad.moviehub.core.utils

import com.riofuad.moviehub.core.data.source.local.entity.MovieEntity
import com.riofuad.moviehub.core.data.source.remote.response.MovieResponse
import com.riofuad.moviehub.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map { movieResponse ->

            val movie = MovieEntity(
                movieId = movieResponse.id,
                name = movieResponse.originalTitle,
                overview = movieResponse.overview,
                posterPath = movieResponse.posterPath,
                releaseDate = movieResponse.releaseDate,
                voteAverage = movieResponse.voteAverage,
                voteCount = movieResponse.voteCount,
                isFavorite = false,
                originalLangugage = movieResponse.originalLanguage
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.movieId,
                name = it.name.toString(),
                overview = it.overview,
                poster = it.posterPath,
                isFavorite = it.isFavorite,
                vote_average = it.voteAverage,
                release_date = it.releaseDate,
                vote_count = it.voteCount,
                original_language = it.originalLangugage
            )
        }

    fun mapDomainToEntity(input: Movie) =
        MovieEntity(
            movieId = input.id,
            overview = input.overview,
            name = input.name,
            posterPath = input.poster,
            voteAverage = input.vote_average,
            isFavorite = input.isFavorite,
            releaseDate = input.release_date,
            voteCount = input.vote_count,
            originalLangugage = input.original_language
        )
}