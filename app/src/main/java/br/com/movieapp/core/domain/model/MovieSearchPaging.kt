package br.com.movieapp.core.domain.model

data class MovieSearchPaging(
    val page: Int,
    val movieSearches: List<MovieSearch>,
    val totalPages: Int,
    val totalResults: Int
)