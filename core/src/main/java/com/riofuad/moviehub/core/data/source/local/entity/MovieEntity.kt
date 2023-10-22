package com.riofuad.moviehub.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "movieList")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val movieId: Int,
    @ColumnInfo(name = "title")
    val name: String? = null,
    @ColumnInfo(name = "overview")
    val overview: String? = null,
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,
    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = null,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = null,
    @ColumnInfo(name = "original_language")
    val originalLangugage: String? = null,
    @ColumnInfo(name = "bookmarked")
    var isFavorite: Boolean = false,
) : Parcelable