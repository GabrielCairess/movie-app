package br.com.movieapp.core.domain.model

class MovieSearchPagingFactory {

    fun create() = MovieSearchPaging(
        page = 1,
        totalPages = 1,
        totalResults = 2,
        movieSearches = listOf(
            MovieSearch(
                id = 1,
                voteAverage = 7.1,
                imageUrl = ""
            ),
            MovieSearch(
                id = 2,
                voteAverage = 10.0,
                imageUrl = ""
            )
        )
    )
}