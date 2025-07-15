package br.com.movieapp.core.domain.model

data class MoviePaging(
    val page: Int,
    val movies: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)