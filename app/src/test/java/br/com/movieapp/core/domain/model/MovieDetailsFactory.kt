package br.com.movieapp.core.domain.model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

class MovieDetailsFactory {

    fun create(poster: Poster) = when (poster) {
        Poster.Avengers -> {
            MovieDetails(
                id = 1,
                title = "Avengers",
                genres = listOf("Action", "Adventure", "Fantasy"),
                overview = LoremIpsum(100).values.first(),
                backDropPath = "url",
                releaseDate = "04/05/2020",
                duration = 120,
                voteCount = 1000,
                voteAverage = 8.9
            )
        }

        Poster.JohnWick -> {
            MovieDetails(
                id = 1,
                title = "John Wick",
                genres = listOf("Action", "Adventure", "Fantasy"),
                overview = LoremIpsum(100).values.first(),
                backDropPath = "url",
                releaseDate = "04/05/2020",
                duration = 120,
                voteCount = 1000,
                voteAverage = 7.8
            )
        }
    }

    sealed class Poster {
        object Avengers: Poster()
        object JohnWick: Poster()
    }
}