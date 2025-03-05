package br.com.movieapp.search_movie_feature.domain.source

import br.com.movieapp.core.data.remote.response.SearchResponse
import br.com.movieapp.core.paging.MovieSearchPagingSource

interface MovieSearchRemoteDataSource {
    fun getSearchMoviePagingSource(query: String): MovieSearchPagingSource

    suspend fun getSearchMovie(query: String, page: Int): SearchResponse
}