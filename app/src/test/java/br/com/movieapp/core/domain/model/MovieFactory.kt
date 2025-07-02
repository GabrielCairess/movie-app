package br.com.movieapp.core.domain.model

import br.com.movieapp.core.domain.Movie

class MovieFactory {

    fun create(poster: Poster) = when (poster) {
        Poster.Avengers -> {
            Movie(
                id = 1,
                title = "Avengers",
                voteAverage = 7.1,
                imageUrl = ""
            )
        }

        Poster.JohnWick -> {
            Movie(
                id = 2,
                title = "Jhon Wick",
                voteAverage = 10.0,
                imageUrl = ""
            )
        }
    }

    sealed class Poster {
        object Avengers: Poster()
        object JohnWick: Poster()
    }
}