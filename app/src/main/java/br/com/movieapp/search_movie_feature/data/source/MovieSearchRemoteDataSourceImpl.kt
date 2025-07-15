package br.com.movieapp.search_movie_feature.data.source

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.core.data.remote.response.SearchResponse
import br.com.movieapp.core.domain.model.MovieSearch
import br.com.movieapp.core.domain.model.MovieSearchPaging
import br.com.movieapp.core.paging.MovieSearchPagingSource
import br.com.movieapp.search_movie_feature.data.mapper.toMovieSearch
import br.com.movieapp.search_movie_feature.domain.source.MovieSearchRemoteDataSource
import javax.inject.Inject
import kotlin.Int

class MovieSearchRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : MovieSearchRemoteDataSource {
    override fun getSearchMoviePagingSource(query: String): MovieSearchPagingSource {
        return MovieSearchPagingSource(query, this)
    }

    override suspend fun getSearchMovie(query: String, page: Int): MovieSearchPaging {
        val response = service.searchMovies(query, page)
        return MovieSearchPaging(
            page = response.page,
            totalPages = response.totalPages,
            movieSearches = response.results.map { it.toMovieSearch() },
            totalResults = response.totalResults
        )
    }
}