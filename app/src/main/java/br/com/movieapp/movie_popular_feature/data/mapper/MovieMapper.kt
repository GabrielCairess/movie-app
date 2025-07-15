package br.com.movieapp.movie_popular_feature.data.mapper

import br.com.movieapp.core.data.remote.model.MovieResult
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.utils.toPostUrl

fun List<MovieResult>.toMovie(): List<Movie> = map {
    Movie(
        id = it.id,
        title = it.title,
        voteAverage = it.voteAverage,
        imageUrl = it.posterPath?.toPostUrl() ?: ""
    )
}

fun MovieResult.toMovie() = Movie(
    id = id,
    title = title,
    voteAverage = voteAverage,
    imageUrl = posterPath?.toPostUrl() ?: ""
)