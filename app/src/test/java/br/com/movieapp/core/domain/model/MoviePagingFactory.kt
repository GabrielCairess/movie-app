package br.com.movieapp.core.domain.model

class MoviePagingFactory {

    fun create() = MoviePaging(
        page = 1,
        totalPages = 1,
        totalResults = 2,
        movies = listOf(
            Movie(
                id = 1,
                title = "Avengers",
                voteAverage = 7.1,
                imageUrl = ""
            ),
            Movie(
                id = 2,
                title = "Jhon Wick",
                voteAverage = 10.0,
                imageUrl = ""
            )
        )
    )
}