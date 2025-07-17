package br.com.movieapp.movie_detail_feature.data.repository

import androidx.paging.PagingSource
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.core.paging.MovieSimilarPagingSource
import br.com.movieapp.movie_detail_feature.domain.repository.MovieDetailsRepository
import br.com.movieapp.movie_detail_feature.domain.source.MovieDetailsRemoteDataSource
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return movieDetailsRemoteDataSource.getMovieDetails(movieId)
    }

    override fun getMovieSimilar(movieId: Int): PagingSource<Int, Movie> {
        return MovieSimilarPagingSource(
            movieId = movieId,
            remoteDataSource = movieDetailsRemoteDataSource
        )
    }
}