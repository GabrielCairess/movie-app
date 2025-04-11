package br.com.movieapp.movie_detail_feature.data.mapper

import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.domain.MovieDetails

fun MovieDetails.toMovie() = Movie(
    id = id,
    title = title,
    imageUrl = backDropPath.toString(),
    voteAverage = voteAverage
)