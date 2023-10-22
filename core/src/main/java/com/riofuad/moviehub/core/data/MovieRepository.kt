package com.riofuad.moviehub.core.data

import com.riofuad.moviehub.core.data.source.local.LocalDataSource
import com.riofuad.moviehub.core.data.source.remote.RemoteDataSource
import com.riofuad.moviehub.core.data.source.remote.response.MovieResponse
import com.riofuad.moviehub.core.data.source.remote.network.ApiResponse
import com.riofuad.moviehub.core.domain.model.Movie
import com.riofuad.moviehub.core.domain.repository.IMovieRepository
import com.riofuad.moviehub.core.utils.AppExecutors
import com.riofuad.moviehub.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> {
        return object :
            NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

    override fun searchMovie(value: String): Flow<List<Movie>> {
        return localDataSource.searchMovie(value).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }
}