package br.com.movieapp.core.domain

data class MovieDetails(
    val id: Int,
    val title: String,
    val genres: List<String>,
    val overview: String?,
    val backDropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double = 0.0,
    val duration: Int = 0,
    val voteCount: Int = 0
)
