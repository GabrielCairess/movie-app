package br.com.movieapp.core.domain.model

import br.com.movieapp.core.domain.MovieSearch

class MovieSearchFactory {

    fun create(poster: Poster) = when (poster) {
        Poster.Avengers -> {
            MovieSearch(
                id = 1,
                voteAverage = 7.1,
                imageUrl = ""
            )
        }

        Poster.JohnWick -> {
            MovieSearch(
                id = 2,
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