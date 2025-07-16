package br.com.movieapp.movie_detail_feature.data.source

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.core.data.remote.response.MovieResponse
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.core.domain.model.MoviePaging
import br.com.movieapp.core.paging.MovieSimilarPagingSource
import br.com.movieapp.core.utils.toBackdropUrl
import br.com.movieapp.movie_detail_feature.domain.source.MovieDetailsRemoteDataSource
import br.com.movieapp.movie_popular_feature.data.mapper.toMovie
import javax.inject.Inject

class MovieDetailsRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : MovieDetailsRemoteDataSource {

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        val response = service.getMovie(movieId)
        val genres = response.genres.map { it.name }
        return MovieDetails(
            id = response.id,
            title = response.title,
            overview = response.overview,
            genres = genres,
            releaseDate = response.releaseDate,
            backDropPath = response.backdropPath.toBackdropUrl(),
            voteAverage = response.voteAverage,
            duration = response.runtime,
            voteCount = response.voteCount
        )
    }

    override suspend fun getMoviesSimilar(page: Int, movieId: Int): MoviePaging {
        val response = service.getMoviesSimilar(movieId, page)
        return MoviePaging(
            page = response.page,
            totalPages = response.totalPages,
            movies = response.results.map { it.toMovie() },
            totalResults = response.totalResults
        )
    }

    override fun getSimilarMoviesPagingSource(movieId: Int): MovieSimilarPagingSource {
        return MovieSimilarPagingSource(movieId, this)
    }
}